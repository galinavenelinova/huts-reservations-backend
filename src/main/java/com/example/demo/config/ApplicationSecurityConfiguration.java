//package com.example.demo.config;
//
//import com.example.demo.service.services.UserService;
//import com.example.demo.web.filters.JwtAuthenticationFilter;
//import com.example.demo.web.filters.JwtAuthorizationFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@EnableWebSecurity
//public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
//    private final UserService userService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    public ApplicationSecurityConfiguration(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.userService = userService;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/api/users/register").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), this.userService))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userService))
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
//        source.registerCorsConfiguration("/**"
//                , corsConfiguration);
//        return source;
//    }
//
////    @Override
////    public void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////                .userDetailsService(this.userService)
////                .passwordEncoder(this.bCryptPasswordEncoder);
////    }
//}
