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

package io.github.ehlxr.token;


import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import jodd.props.Props;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentMap;

/**
 * token版本控制工厂类
 * User: erin
 * Date: 14-10-30
 * Time: 下午5:34
 */
public class TokenVersionFactory {

    private static final String TOKEN_VERSION = "tokenVersion";
    private static final String RESOURCE_NAME = "token_version.properties";

    private static Props properties = null;

    protected static ConcurrentMap<String, TokenVersionConfig> versionMap = Maps.newConcurrentMap();

    public static TokenVersionConfig getTokenConfig(int version) throws IOException {

        String versionString = TokenVersionEnum.getStringVersion(version);
        TokenVersionConfig oAuthConsumer = versionMap.get(buildVersionKey(versionString));
        if (oAuthConsumer == null) {
            oAuthConsumer = newResource(RESOURCE_NAME, versionString);
            versionMap.putIfAbsent(buildVersionKey(versionString), oAuthConsumer);
        }
        return oAuthConsumer;
    }

    private static synchronized TokenVersionConfig newResource(String resourceName, String versionStr) throws IOException {
        properties = new Props();
        InputStream input = TokenVersionConfig.class.getClassLoader().getResourceAsStream(resourceName);
        properties.load(input);
        TokenVersionConfig tokenVersionConfig = new TokenVersionConfig();
        tokenVersionConfig.setVersion(Integer.valueOf(versionStr));
        tokenVersionConfig.setCheck_token_key(getValue("check_token_key", versionStr));
        tokenVersionConfig.setCreate_token_key(getValue("create_token_key", versionStr));
        tokenVersionConfig.setCheck(getValue("check", versionStr));
        return tokenVersionConfig;
    }

    private static String getValue(String name, String provider) {
        name = TOKEN_VERSION + "." + name;
        String url = properties.getValue(name, provider);
        if (Strings.isNullOrEmpty(url)) {
            return null;
        }
        return url;
    }

    private static String buildVersionKey(String versionStr) {
        return versionStr + "_" + RESOURCE_NAME;
    }

}
