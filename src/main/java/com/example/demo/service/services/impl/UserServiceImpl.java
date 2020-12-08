package com.example.demo.service.services.impl;

import com.example.demo.data.models.User;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.service.models.UserServiceModel;
import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserDetailsOutputModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetailsOutputModel loadUserByEmail(String email) {
        UserServiceModel userServiceModel = this.userRepository.findByEmail(email)
                .map(m -> modelMapper.map(m, UserServiceModel.class)).orElse(null);
        return this.modelMapper.map(userServiceModel, UserDetailsOutputModel.class);
    }

    @Override
    public UserServiceModel loadUserByUsername(String username) {
        return this.userRepository.findByUsername(username).map(u -> this.modelMapper.map(u, UserServiceModel.class)).orElse(null);
    }

    @Override
    public UserOutputModel register(UserInputModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        User user = this.userRepository.save(modelMapper.map(userModel, User.class));
        return this.modelMapper.map(user, UserOutputModel.class);
    }

    @Override
    public UserOutputModel login(UserInputModel userModel) {
        User user = this.userRepository.findByEmail(userModel.getEmail()).orElse(null);
        if(user == null || !passwordEncoder.matches(userModel.getPassword(), user.getPassword())) {
            return null;
        }else {
            return this.modelMapper.map(user, UserOutputModel.class);
        }
    }
}
