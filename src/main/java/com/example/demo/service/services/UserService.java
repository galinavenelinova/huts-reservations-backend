package com.example.demo.service.services;

import com.example.demo.web.models.UserDetailsModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    UserOutputModel register(UserInputModel userModel);

    UserDetailsModel getUserDetails(String userId);

    UserDetailsModel updateUserDetails(String username, UserInputModel userModel);

    List<UserOutputModel> getAllUsers();
}
