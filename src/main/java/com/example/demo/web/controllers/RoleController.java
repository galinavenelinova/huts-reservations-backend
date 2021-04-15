package com.example.demo.web.controllers;

import com.example.demo.service.services.RoleService;
import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api", consumes = "application/json")
public class RoleController {
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping("/users/change-role")
    public ResponseEntity<List<UserOutputModel>> makeUserAdmin(@RequestBody String username) {
        this.roleService.makeUserAdmin(username);
        List<UserOutputModel> usersList = this.userService.getAllUsers();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
}
