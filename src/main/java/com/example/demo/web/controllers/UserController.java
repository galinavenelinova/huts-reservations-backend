package com.example.demo.web.controllers;

import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserDetailsOutputModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api/users", consumes = "application/json")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserOutputModel> register(@RequestBody UserInputModel userModel) {
       UserOutputModel userOutputModel = this.userService.register(userModel);
       if(userOutputModel == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       } else {
           return new ResponseEntity<>(userOutputModel, HttpStatus.OK);
       }
    }

    @PostMapping("/login")
    public ResponseEntity<UserOutputModel> login(@RequestBody UserInputModel userModel) {
        UserOutputModel userOutputModel = this.userService.login(userModel);
        if(userOutputModel == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(userOutputModel, HttpStatus.OK);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<UserOutputModel> logout(@RequestBody UserInputModel userModel) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDetailsOutputModel> getUserDetails(@RequestBody UserInputModel userModel) {
        UserDetailsOutputModel userDetailsOutputModel = this.userService.loadUserByEmail(userModel.getEmail());
        return new ResponseEntity<>(userDetailsOutputModel, HttpStatus.OK);
    }
}
