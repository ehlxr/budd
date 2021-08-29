package io.github.ehlxr.handler;

import io.github.ehlxr.common.Constant;
import io.github.ehlxr.common.Result;
import io.github.ehlxr.enums.CodeEnum;
import io.github.ehlxr.exception.ServiceDataException;
import org.assertj.core.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ehlxr
 * @since 2020/4/20.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> defaultExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("request: {}, exception: ", request.getRequestURI(), e);

        // SendAlarmUtil.send(String.format("未知异常 request: %s, exception: %s", request.getRequestURI(), ExceptionUtils.getStackTrace(e)),
        //         (t, u) -> log.info("send exception message to dingtalk result {} ", t, u));

        String msg = CodeEnum.UNKNOWN_EXCEPTION.getMessage();
        String traceId = MDC.get(Constant.LOG_TRACE_ID);
        if (!Strings.isNullOrEmpty(traceId)) {
            msg = String.format("%s（%s）", msg, traceId);
        }

        return Result.of(CodeEnum.UNKNOWN_EXCEPTION.getCode(), null, msg);
    }

    @ExceptionHandler(ServiceDataException.class)
    @ResponseBody
    public Result<?> serviceDataExceptionHandler(HttpServletRequest request, ServiceDataException e) {
        log.error("request: {} service exception: ", request.getRequestURI(), e);

        // SendAlarmUtil.send(String.format("业务异常 request: %s, service exception: %s", request.getRequestURI(), ExceptionUtils.getStackTrace(e)),
        //         (t, u) -> log.info("send service exception message to dingtalk result {} ", t, u));

        String msg = e.getMessage();
        String traceId = MDC.get(Constant.LOG_TRACE_ID);
        if (!Strings.isNullOrEmpty(traceId)) {
            msg = String.format("%s（%s）", msg, traceId);
        }

        return Result.of(e.getCode(), null, msg);
    }

    @ExceptionHandler(MultipartException.class)
    public String handleError(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return "redirect:/uploadStatus";
    }
}
