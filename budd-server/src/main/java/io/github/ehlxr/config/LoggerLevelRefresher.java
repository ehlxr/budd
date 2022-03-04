/*
 * The MIT License (MIT)
 *
 * Copyright © 2022 xrv <xrg@live.com>
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

package io.github.ehlxr.config;

// import com.ctrip.framework.apollo.model.ConfigChangeEvent;
// import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggingSystem;
// import org.springframework.stereotype.Component;

/**
 * 结合 apollo 配置中心动态刷新（logging.level.）日志级别
 *
 * @author ehlxr
 * @since 2022-01-06 17:43.
 */
// @Component
public class LoggerLevelRefresher {
    private static final Logger logger = LoggerFactory.getLogger(LoggerLevelRefresher.class);
    private static final String LOGGER_TAG = "logging.level.";
    private final LoggingSystem loggingSystem;

    @Autowired
    public LoggerLevelRefresher(LoggingSystem loggingSystem) {
        this.loggingSystem = loggingSystem;
    }

    // @ApolloConfigChangeListener(interestedKeyPrefixes = LOGGER_TAG)
    // private void onChange(ConfigChangeEvent changeEvent) {
    //     for (String key : changeEvent.changedKeys()) {
    //         String strLevel = changeEvent.getChange(key).getNewValue();
    //         loggingSystem.setLogLevel(key.replaceAll(LOGGER_TAG, ""),
    //                 LogLevel.valueOf(strLevel.toUpperCase()));
    //
    //         logger.info("logging changed: {}, oldValue: {}, newValue: {}",
    //                 key, changeEvent.getChange(key).getOldValue(), strLevel);
    //     }
    // }

}