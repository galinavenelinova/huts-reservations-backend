package com.example.demo.service.services;

import com.example.demo.config.exceptions.InvalidUserException;
import com.example.demo.data.models.User;
import com.example.demo.web.models.UserDetailsModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.List;


public interface UserService extends UserDetailsService {
    UserOutputModel register(UserInputModel userModel) throws InvalidUserException;

    UserDetailsModel getUserDetails(String username);

    UserDetailsModel updateUserDetails(String username, UserInputModel userModel);

    List<UserOutputModel> getAllUsers();
}
