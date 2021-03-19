package com.example.demo.web.controllers;

import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserDetailsModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

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
        return new ResponseEntity<>(userOutputModel, HttpStatus.OK);
    }

    @GetMapping("/users/profile")
    public ResponseEntity<UserDetailsModel> getUserDetails(Principal principal) {
        String username = principal.getName();
        UserDetailsModel userDetailsOutputModel = this.userService.getUserDetails(username);
        userDetailsOutputModel.setRole(this.userService.loadUserByUsername(principal.getName()).getAuthorities().stream().findFirst().orElse(null).getAuthority());
        return new ResponseEntity<>(userDetailsOutputModel, HttpStatus.OK);
    }

    @PostMapping("/users/profile")
    public ResponseEntity<UserDetailsModel> updateUserDetails(Principal principal, @RequestBody UserInputModel userModel) {
        UserDetailsModel userDetailsOutputModel = this.userService.updateUserDetails(principal.getName(), userModel);
        userDetailsOutputModel.setRole(this.userService.loadUserByUsername(principal.getName()).getAuthorities().stream().findFirst().orElse(null).getAuthority());
        return new ResponseEntity<>(userDetailsOutputModel, HttpStatus.OK);
    }

    @GetMapping("/users/list")
    public ResponseEntity<List<UserOutputModel>> getUsersList(Principal principal) {
        List<UserOutputModel> usersList = this.userService.getAllUsers();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
}
