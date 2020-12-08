package com.example.demo.web.filters;

import com.example.demo.data.models.User;
import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserInputModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Order(0)
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserInputModel loginBindingModel = new ObjectMapper()
                    .readValue(request.getInputStream(), UserInputModel.class);

            return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginBindingModel.getUsername(),
                            loginBindingModel.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException ignored) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = ((User) authResult.getPrincipal());

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1200000000))
                .signWith(SignatureAlgorithm.HS256, "Secret".getBytes())
                .compact();

        response.getWriter()
                .append(token);

        response.addHeader("Authorization", "Bearer " + token);
    }
}
