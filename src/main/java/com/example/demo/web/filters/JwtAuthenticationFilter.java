package com.example.demo.web.filters;

import com.example.demo.data.models.Role;
import com.example.demo.data.models.User;
import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import io.jsonwebtoken.Jwts;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        String username = ((UserDetails) authResult.getPrincipal()).getUsername();
        UserDetails userDetails = userService.loadUserByUsername(username);
        User user = new User();
        user.setUsername(userDetails.getUsername());
        List<Role> roles = userDetails.getAuthorities().stream().map(a -> (Role)a).collect(Collectors.toList());
        user.setAuthorities(roles);
        String authority = user.getAuthorities().get(0).getAuthority();

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 1200000000))
                .claim("role", authority)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS256, "Secret".getBytes())
                .compact();

        UserOutputModel userOutputModel = new UserOutputModel();
        userOutputModel.setUsername(user.getUsername());
        userOutputModel.setRole(authority);
        String json = new Gson().toJson(userOutputModel);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

        response.addHeader("Authorization", "Bearer " + token);
    }
}
