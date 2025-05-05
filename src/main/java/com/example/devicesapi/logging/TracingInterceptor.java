package com.example.devicesapi.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;



@NoArgsConstructor
@Slf4j
public class TracingInterceptor implements HandlerInterceptor {
    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(final HttpServletRequest request, @NonNull final HttpServletResponse response,
                             @NonNull final Object object) {
        final long currentTimeMillis = System.currentTimeMillis();
        request.setAttribute(START_TIME, currentTimeMillis);

        log.info("Enter endpoint {} at {}", request.getRequestURI(), currentTimeMillis);
        log.info("Start processing request.");
        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, @NonNull final HttpServletResponse response,
                                @NonNull final Object handler, final Exception ex) {
        if (request.getAttribute(START_TIME) != null && log.isInfoEnabled()) {
            final Long timeElapsed = System.currentTimeMillis() - (Long) request.getAttribute(START_TIME);
            log.info("Time elapsed for {} is {} ms", request.getRequestURI(), timeElapsed);
        }
        MDC.clear();
    }
}