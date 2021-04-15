package com.example.demo.config;

import com.example.demo.web.interceptors.ReservationLoggerInterceptor;
import com.example.demo.web.interceptors.RoleChangeLoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoadInterceptorConfig implements WebMvcConfigurer {
    private final ReservationLoggerInterceptor reservationLoggerInterceptor;
    private final RoleChangeLoggerInterceptor roleChangeLoggerInterceptor;

    public LoadInterceptorConfig(ReservationLoggerInterceptor reservationLoggerInterceptor, RoleChangeLoggerInterceptor roleChangeLoggerInterceptor) {
        this.reservationLoggerInterceptor = reservationLoggerInterceptor;
        this.roleChangeLoggerInterceptor = roleChangeLoggerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.
                addInterceptor(reservationLoggerInterceptor).
                addPathPatterns("/api/huts/**");
        registry.
                addInterceptor(roleChangeLoggerInterceptor).
                addPathPatterns("/api/users/change-role");
    }
}
