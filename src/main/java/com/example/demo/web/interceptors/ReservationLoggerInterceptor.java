package com.example.demo.web.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ReservationLoggerInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationLoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        LOGGER.info("~~~ ReservationLoggerInterceptor.preHandle()");
        LOGGER.info("Request URL: " + request.getRequestURL() + "; Request method: " + request.getMethod());
        LOGGER.info("Request by user: " + request.getUserPrincipal().getName());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        LOGGER.info("~~~ ReservationLoggerInterceptor.postHandle()");
        LOGGER.info("Response status: " + response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        LOGGER.info("~~~ ReservationLoggerInterceptor.afterCompletion()");
        LOGGER.info("Time for completing request to " + request.getRequestURL() + ": " + (endTime - startTime) + " ms");
    }
}
