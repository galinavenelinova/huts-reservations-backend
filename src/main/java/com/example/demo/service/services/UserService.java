package com.example.demo.service.services;

import com.example.demo.service.models.UserServiceModel;
import com.example.demo.web.models.UserDetailsOutputModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;


public interface UserService {
    UserDetailsOutputModel loadUserByEmail(String email);

    UserServiceModel loadUserByUsername(String username);

    UserOutputModel register(UserInputModel userModel);

    UserOutputModel login(UserInputModel userModel);
}
