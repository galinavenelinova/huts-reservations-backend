package com.example.demo.web.filters;

import com.example.demo.service.models.UserServiceModel;
import com.example.demo.service.services.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Order(1)
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token = this.getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;

        if (token != null) {
            String username = Jwts.parser()
                    .setSigningKey("Secret".getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if (username != null) {
//                UserDetails userData = this.userService
//                        .loadUserByUsername(username);

                UserServiceModel userModel = this.userService.loadUserByUsername(username);

                usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(
                        username,
                        null
                );
            }
        }

        return usernamePasswordAuthenticationToken;
    }
}
