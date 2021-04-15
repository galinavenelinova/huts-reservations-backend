package com.example.demo.web.interceptors;

import com.example.demo.data.models.Role;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Component
public class RoleChangeLoggerInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleChangeLoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, ServletException {
        LOGGER.info("~~~ RoleChangeLoggerInterceptor.preHandle()");
        LOGGER.info("Request URL: " + request.getRequestURL() + "; Request method: " + request.getMethod());
        LOGGER.info("Role change requested by user: " + request.getUserPrincipal().getName());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        LOGGER.info("~~~ RoleChangeLoggerInterceptor.postHandle()");
        LOGGER.info("Response status: " + response.getStatus());
    }
}
