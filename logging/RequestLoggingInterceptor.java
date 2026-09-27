package com.aura.godeliver.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final String START_TIME_ATTRIBUTE =
            RequestLoggingInterceptor.class.getName() + ".START_TIME";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {

        request.setAttribute(
                START_TIME_ATTRIBUTE,
                System.currentTimeMillis()
        );

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {

        Long startTime =
                (Long) request.getAttribute(START_TIME_ATTRIBUTE);

        long duration = startTime != null
                ? System.currentTimeMillis() - startTime
                : 0;

        String controller = "Unknown";
        String action = "Unknown";

        if (handler instanceof HandlerMethod handlerMethod) {

            controller =
                    handlerMethod
                            .getBeanType()
                            .getSimpleName();

            action =
                    handlerMethod
                            .getMethod()
                            .getName();
        }

        String queryParameters =
                getSafeQueryParameters(request);

        int status = response.getStatus();

        if (ex != null) {

            log.error(
                    "HTTP request failed | method={} | uri={} | controller={} | action={} | status={} | duration={}ms | params={} | exception={} | message={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    controller,
                    action,
                    status,
                    duration,
                    queryParameters,
                    ex.getClass().getSimpleName(),
                    ex.getMessage(),
                    ex
            );

            return;
        }

        if (status >= 500) {

            log.error(
                    "HTTP server error | method={} | uri={} | controller={} | action={} | status={} | duration={}ms | params={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    controller,
                    action,
                    status,
                    duration,
                    queryParameters
            );

        } else if (status >= 400) {

            log.warn(
                    "HTTP client error | method={} | uri={} | controller={} | action={} | status={} | duration={}ms | params={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    controller,
                    action,
                    status,
                    duration,
                    queryParameters
            );

        } else {

            log.info(
                    "HTTP request completed | method={} | uri={} | controller={} | action={} | status={} | duration={}ms | params={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    controller,
                    action,
                    status,
                    duration,
                    queryParameters
            );
        }
    }

    private String getSafeQueryParameters(
            HttpServletRequest request
    ) {

        Map<String, String[]> parameterMap =
                request.getParameterMap();

        if (parameterMap.isEmpty()) {
            return "{}";
        }

        return parameterMap.entrySet()
                .stream()
                .map(entry -> {

                    String key = entry.getKey();

                    if (isSensitiveParameter(key)) {
                        return key + "=***";
                    }

                    String value =
                            String.join(",", entry.getValue());

                    return key + "=" + value;
                })
                .collect(Collectors.joining(
                        ", ",
                        "{",
                        "}"
                ));
    }

    private boolean isSensitiveParameter(String parameterName) {

        String name =
                parameterName.toLowerCase();

        return name.contains("password")
                || name.contains("token")
                || name.contains("secret")
                || name.contains("authorization")
                || name.contains("apikey")
                || name.contains("api_key")
                || name.contains("refresh");
    }
}