package com.example.demo.service.services;

import com.example.demo.web.models.UserDetailsModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    UserOutputModel register(UserInputModel userModel);

    UserOutputModel login(UserInputModel userModel);

    UserDetailsModel getUserDetails(String userId);

    UserDetailsModel updateUserDetails(String userId, UserInputModel userModel);
}
