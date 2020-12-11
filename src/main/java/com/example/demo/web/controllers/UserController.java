package com.example.demo.web.controllers;

import com.example.demo.data.models.User;
import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserDetailsOutputModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RestController
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api/users", consumes = "application/json")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserOutputModel> register(@RequestBody UserInputModel userModel, HttpServletResponse response) {
        System.out.println("register: " + userModel.getEmail());
        UserOutputModel userOutputModel = this.userService.register(userModel);
        if (userOutputModel == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            String token = Jwts.builder()
                    .setSubject(userOutputModel.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + 1200000000))
                    .signWith(SignatureAlgorithm.HS256, "Secret".getBytes())
                    .compact();
            HttpHeaders responseHeaders = new HttpHeaders();
            Cookie cookie = new Cookie("auth-cookie", token);
            response.addCookie(cookie);
            return new ResponseEntity<>(userOutputModel, responseHeaders, HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserOutputModel> login(@RequestBody UserInputModel userModel, HttpServletResponse response) throws IOException, ServletException {
        UserOutputModel userOutputModel = this.userService.login(userModel);
        if (userOutputModel == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
//            String token = Jwts.builder()
//                    .setSubject(userOutputModel.getUsername())
//                    .setExpiration(new Date(System.currentTimeMillis() + 1200000000))
//                    .signWith(SignatureAlgorithm.HS256, "Secret".getBytes())
//                    .compact();
            HttpHeaders responseHeaders = new HttpHeaders();
            Cookie cookie = new Cookie("email", userOutputModel.getEmail());
            response.addCookie(cookie);

            return new ResponseEntity<>(userOutputModel, responseHeaders, HttpStatus.OK);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<UserOutputModel> logout(@RequestBody UserInputModel userModel, HttpServletResponse response) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDetailsOutputModel> getUserDetails(@CookieValue(name="email") String email, HttpServletRequest request) {
        UserDetailsOutputModel userDetailsOutputModel = this.userService.getUserDetails(email);
//        UserDetailsOutputModel userDetailsOutputModel = this.userService.loadUserByEmail(userInputModel.getEmail());
        return new ResponseEntity<>(userDetailsOutputModel, HttpStatus.OK);
    }
}
