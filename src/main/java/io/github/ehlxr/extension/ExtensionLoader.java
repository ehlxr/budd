/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.extension;

import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/**
 * 加载和管理扩展（简化版 Dubbo SPI）
 *
 * @author ehlxr
 */
@SuppressWarnings("unused")
public class ExtensionLoader<T> {
    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);

    private static final String EXTENSIONS_DIRECTORY = "META-INF/extensions/";
    private static final String EXTENSIONS_INTERNAL_DIRECTORY = "META-INF/extensions/internal/";

    private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*,+\\s*");

    private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();

    private final Class<T> type;
    private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
    private final ConcurrentMap<Class<?>, String> cachedNames = new ConcurrentHashMap<>();
    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
    private final Map<String, IllegalStateException> exceptions = new ConcurrentHashMap<>();
    private String defaultExtension;

    private ExtensionLoader(Class<T> type) {
        this.type = type;
    }

    /**
     * {@link ExtensionLoader} 的工厂方法。
     *
     * @param type 扩展点接口类型
     * @param <T>  扩展点类型
     * @return {@link ExtensionLoader} 实例
     * @throws IllegalArgumentException 参数为 <code>null</code>；
     *                                  或是扩展点接口上没有 {@link SPI} 注解。
     */
    @SuppressWarnings("unchecked")
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("SPI type == null");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("SPI type(" + type.getName() + ") is not interface!");
        }
        if (!type.isAnnotationPresent(SPI.class)) {
            throw new IllegalArgumentException("type(" + type.getName() +
                    ") is not a extension, because WITHOUT @SPI Annotation!");
        }

        ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        if (loader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<>(type));
            loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return loader;
    }

    /**
     * Get the String of Throwable, like the output of {@link Throwable#printStackTrace()}.
     *
     * @param throwable the input throwable.
     */
    private static String throwable2String(Throwable throwable) {
        StringWriter w = new StringWriter(1024);
        try (PrintWriter p = new PrintWriter(w)) {
            throwable.printStackTrace(p);
            return w.toString();
        }
    }

    public T getExtension(String name) {
        if (StringUtil.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("SPI name == null");
        }

        Holder<Object> holder = cachedInstances.get(name);
        if (holder == null) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }

        Object instance = holder.get();
        if (instance == null) {
            synchronized (cachedInstances) {
                instance = holder.get();
                if (instance == null) {
                    instance = createExtension(name);
                    holder.set(instance);
                }
            }
        }
        //noinspection unchecked
        return (T) instance;
    }

    /**
     * 返回缺省的扩展。
     *
     * @throws IllegalStateException 指定的扩展没有设置缺省扩展点
     */
    public T getDefaultExtension() {
        loadExtensionClasses0();

        if (null == defaultExtension || defaultExtension.length() == 0) {
            throw new IllegalStateException("No default extension on extension " + type.getName());
        }
        return getExtension(defaultExtension);
    }

    /**
     * 获取扩展点实现的所有扩展点名。
     *
     * @since 0.1.0
     */
    public Set<String> getSupportedExtensions() {
        Map<String, Class<?>> classes = getExtensionClasses();
        return Collections.unmodifiableSet(new HashSet<>(classes.keySet()));
    }

    public String getExtensionName(Class<?> spi) {
        getExtensionClasses();
        return cachedNames.get(spi);
    }

    private T createExtension(String name) {
        Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null) {
            throw findExtensionClassLoadException(name);
        }
        try {
            //noinspection unchecked
            T instance = (T) EXTENSION_INSTANCES.get(clazz);
            if (instance == null) {
                EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
                //noinspection unchecked
                instance = (T) EXTENSION_INSTANCES.get(clazz);
            }

            return instance;
        } catch (Throwable t) {
            throw new IllegalStateException("SPI instance(name: " + name + ", class: " +
                    type + ")  could not be instantiated: " + t.getMessage(), t);
        }
    }

    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.get();
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.get();
                if (classes == null) {
                    loadExtensionClasses0();
                    classes = cachedClasses.get();
                }
            }
        }
        return classes;
    }

    private IllegalStateException findExtensionClassLoadException(String name) {
        for (Map.Entry<String, IllegalStateException> entry : exceptions.entrySet()) {
            if (entry.getKey().toLowerCase().contains(name.toLowerCase())) {
                return entry.getValue();
            }
        }

        int i = 1;
        StringBuilder buf = new StringBuilder("No such extension " + type.getName() + " by name " + name);
        for (Map.Entry<String, IllegalStateException> entry : exceptions.entrySet()) {
            if (i == 1) {
                buf.append(", possible causes: ");
            }

            buf.append("\r\n(");
            buf.append(i++);
            buf.append(") ");
            buf.append(entry.getKey());
            buf.append(":\r\n");
            buf.append(throwable2String(entry.getValue()));
        }
        return new IllegalStateException(buf.toString());
    }

    private void loadExtensionClasses0() {
        final SPI annotation = type.getAnnotation(SPI.class);
        if (annotation != null) {
            String value = annotation.value();
            if ((value = value.trim()).length() > 0) {
                String[] names = NAME_SEPARATOR.split(value);
                if (names.length > 1) {
                    throw new IllegalStateException("more than 1 default extension name on extension " +
                            type.getName() + ": " + Arrays.toString(names));
                }
                if (names.length == 1 && names[0].trim().length() > 0) {
                    defaultExtension = names[0].trim();
                }
            }
        }

        Map<String, Class<?>> extensionClasses = new HashMap<>();
        loadFile(extensionClasses, EXTENSIONS_DIRECTORY);
        loadFile(extensionClasses, EXTENSIONS_INTERNAL_DIRECTORY);
        cachedClasses.set(extensionClasses);
    }

    private void loadFile(Map<String, Class<?>> extensionClasses, String dir) {
        String fileName = dir + type.getName();
        try {
            Enumeration<URL> urls;
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
            if (classLoader != null) {
                urls = classLoader.getResources(fileName);
            } else {
                urls = ClassLoader.getSystemResources(fileName);
            }

            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();

                    try (
                            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))
                    ) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            // delete comments
                            final int ci = line.indexOf('#');
                            if (ci >= 0) {
                                line = line.substring(0, ci);
                            }
                            line = line.trim();
                            if (line.length() == 0) {
                                continue;
                            }

                            try {
                                String name = null;
                                int i = line.indexOf('=');

                                if (i > 0) {
                                    name = line.substring(0, i).trim();
                                    line = line.substring(i + 1).trim();
                                }

                                if (line.length() > 0) {
                                    Class<? extends T> clazz = Class.forName(line, true, classLoader).asSubclass(type);
                                    if (!type.isAssignableFrom(clazz)) {
                                        throw new IllegalStateException("Error when load extension class(interface: " +
                                                type.getName() + ", class line: " + clazz.getName() + "), class "
                                                + clazz.getName() + "is not subtype of interface.");
                                    }

                                    if (name == null || name.length() == 0) {
                                        // clazz: xxx.xxx.ZfyAPI
                                        // type: xxx.xxx.API
                                        // -> name: zfy
                                        if (clazz.getSimpleName().length() > type.getSimpleName().length()
                                                && clazz.getSimpleName().endsWith(type.getSimpleName())) {
                                            name = clazz.getSimpleName().substring(0, clazz.getSimpleName().length()
                                                    - type.getSimpleName().length()).toLowerCase();
                                        } else {
                                            throw new IllegalStateException("No such extension name for the class "
                                                    + clazz.getName() + " in the config " + url);
                                        }
                                    }


                                    if (!cachedNames.containsKey(clazz)) {
                                        cachedNames.put(clazz, name);
                                    }

                                    Class<?> c = extensionClasses.get(name);
                                    if (c == null) {
                                        extensionClasses.put(name, clazz);
                                    } else if (c != clazz) {
                                        throw new IllegalStateException("Duplicate extension "
                                                + type.getName() + " name " + name + " on " + c.getName()
                                                + " and " + clazz.getName());
                                    }
                                }
                            } catch (Throwable t) {
                                IllegalStateException e = new IllegalStateException("Failed to load extension class(interface: "
                                        + type + ", class line: " + line + ") in " + url + ", cause: " + t.getMessage(), t);
                                exceptions.put(line, e);
                            }
                        }
                    } catch (Throwable t) {
                        logger.error("Exception when load extension class(interface: " +
                                type + ", class file: " + url + ") in " + url, t);
                    }
                }
            }
        } catch (Throwable t) {
            logger.error("Exception when load extension class(interface: " +
                    type + ", description file: " + fileName + ").", t);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[" + type.getName() + "]";
    }

    /**
     * Holds a value of type <code>T</code>.
     */
    @SuppressWarnings("unused")
    private static final class Holder<T> {
        /**
         * The value contained in the holder.
         */
        private volatile T value;

        /**
         * Creates a new holder with a <code>null</code> value.
         */
        Holder() {
        }

        /**
         * Create a new holder with the specified value.
         *
         * @param value The value to be stored in the holder.
         */
        public Holder(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
        }
    }
}
