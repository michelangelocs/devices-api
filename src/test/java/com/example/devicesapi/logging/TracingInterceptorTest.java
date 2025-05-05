package com.example.devicesapi.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Handler;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TracingInterceptorTest {

    private static final String ENDPOINT = "/api/devices/endpoint";
    private static final String START_TIME = "startTime";

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Handler handler;
    @Mock
    private Exception ex;

    @Test
    void testTracingInterceptorPreHandle() {
        final TracingInterceptor tracingInterceptor = new TracingInterceptor();
        assertTrue(tracingInterceptor.preHandle(request, response, handler));
    }

    @Test
    void testTracingInterceptorAfterCompletion() {
        final TracingInterceptor tracingInterceptor = new TracingInterceptor();
        when(request.getRequestURI()).thenReturn(ENDPOINT);
        assertTrue(tracingInterceptor.preHandle(request, response, handler));

        when(request.getAttribute(START_TIME)).thenReturn(System.currentTimeMillis() - 100);
        tracingInterceptor.afterCompletion(request, response, handler, ex);
    }

}