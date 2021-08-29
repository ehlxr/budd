/*
 * The MIT License (MIT)
 *
 * Copyright © 2021 xrv <xrg@live.com>
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

package io.github.ehlxr.aop;

import com.google.common.base.Strings;
import io.github.ehlxr.common.Constant;
import io.github.ehlxr.util.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 请求日志记录
 *
 * @author ehlxr
 * @since 2021-08-17 22:03.
 */
@Aspect
@Component
public class LogRequestAop {
    private static final Logger log = LoggerFactory.getLogger(LogRequestAop.class);

    // private final AliyunLogManager aliyunLogManager;
    // /**
    //  * aliyun log 配置
    //  */
    // @Value("${aliyun.log.project:}")
    // private String projcet;
    // @Value("${aliyun.log.logstore.request:}")
    // private String logStore;
    // @Value("${aliyun.log.topic:}")
    // private String topic;

    // @Autowired
    // public LogRequestAop(AliyunLogManager aliyunLogManager) {
    //     this.aliyunLogManager = aliyunLogManager;
    // }

    /**
     * 切入点
     */
    @Pointcut("execution(public * io.github.ehlxr.*Controller.*(..)) && !@annotation(io.github.ehlxr.annotations.NoRequestLog)")
    // @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && !@annotation(com.tzld.piaoquan.incentive.annotations.NoRequestLog)")
    public void requestLog() {
    }

    /**
     * 前置操作
     */
    @Before("requestLog()")
    public void beforeLog() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        request.setAttribute(Constant.REQUEST_START_TIME, System.currentTimeMillis());
    }

    /**
     * 后置操作
     */
    @AfterReturning(pointcut = "requestLog()", returning = "returnValue")
    public void afterReturning(JoinPoint point, Object returnValue) {
        logRecord(point, JsonUtils.obj2String(returnValue));
    }

    @AfterThrowing(pointcut = "requestLog()", throwing = "ex")
    public void afterThrowing(JoinPoint point, Exception ex) {
        logRecord(point, ex.getMessage());
    }

    private void logRecord(JoinPoint point, String message) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

            Long startTime = (Long) request.getAttribute(Constant.REQUEST_START_TIME);
            if (Objects.isNull(startTime)) {
                return;
            }

            String query = request.getQueryString();
            String uri = request.getRequestURI();

            Map<String, Object> logMap = new HashMap<>(8);
            logMap.put("url", Strings.isNullOrEmpty(query) ? uri : uri + "?" + query);
            logMap.put("method", request.getMethod());
            logMap.put("header", getHeaders(request));
            logMap.put("elapsedTime", String.valueOf(System.currentTimeMillis() - startTime));
            logMap.put("clientIp", request.getRemoteAddr());
            logMap.put("requestBody", JsonUtils.obj2String(point.getArgs()));
            logMap.put(Constant.LOG_TRACE_ID, Strings.nullToEmpty(MDC.get(Constant.LOG_TRACE_ID)));
            logMap.put("responseBody", message);

            // aliyunLogManager.sendLog(projcet, logStore, topic, logMap);
        } catch (Exception e) {
            log.error("log report request error", e);
        }
    }

    private String getHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        name -> name,
                        request::getHeader)).toString();
    }
}