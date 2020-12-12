package com.example.demo.web.controllers;

import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserDetailsModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api", consumes = "application/json")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserOutputModel> register(@RequestBody UserInputModel userModel, HttpServletResponse response) {
        UserOutputModel userOutputModel = this.userService.register(userModel);
        if (userOutputModel == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            HttpHeaders responseHeaders = new HttpHeaders();
            Cookie cookie = new Cookie("id", userOutputModel.getId());
            response.addCookie(cookie);
            return new ResponseEntity<>(userOutputModel, responseHeaders, HttpStatus.OK);
        }
    }

    @PostMapping("/users/login")
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
            Cookie cookie = new Cookie("id", userOutputModel.getId());
            response.addCookie(cookie);

            return new ResponseEntity<>(userOutputModel, responseHeaders, HttpStatus.OK);
        }
    }

    @PostMapping("/users/logout")
    public ResponseEntity<UserOutputModel> logout() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/users/profile")
    public ResponseEntity<UserDetailsModel> getUserDetails(@CookieValue(name="id") String userId) {
        UserDetailsModel userDetailsOutputModel = this.userService.getUserDetails(userId);
        return new ResponseEntity<>(userDetailsOutputModel, HttpStatus.OK);
    }

    @PostMapping("/users/profile")
    public ResponseEntity<UserDetailsModel> updateUserDetails(@CookieValue(name="id") String userId, @RequestBody UserInputModel userModel) {
        System.out.println("Cookie userId: " + userId);
        System.out.println("userModelEmail: " + userModel.getEmail());
        UserDetailsModel userDetailsOutputModel = this.userService.updateUserDetails(userId, userModel);
//        UserDetailsOutputModel userDetailsOutputModel = this.userService.getUserDetails(userModel.getId());
//        UserDetailsOutputModel userDetailsOutputModel = this.userService.loadUserByEmail(userInputModel.getEmail());
        return new ResponseEntity<>(userDetailsOutputModel, HttpStatus.OK);
    }
}
