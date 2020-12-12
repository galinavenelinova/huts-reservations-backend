package com.example.demo.service.services.impl;

import com.example.demo.data.models.Role;
import com.example.demo.data.models.User;
import com.example.demo.data.repositories.RoleRepository;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserDetailsModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserOutputModel register(UserInputModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        User userInput = modelMapper.map(userModel, User.class);
        Role role = new Role();
        role.setAuthority();
        userInput.setAuthorities(Arrays.asList(role));
        this.roleRepository.save(role);
        User user = this.userRepository.save(userInput);
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

    @Override
    public UserDetailsModel getUserDetails(String userId) {
        User user = this.userRepository.findById(userId).orElse(null);
        return this.modelMapper.map(user, UserDetailsModel.class);
    }

    @Override
    public UserDetailsModel updateUserDetails(String userId, UserInputModel userModel) {
        User user = this.userRepository.findById(userId).orElse(null);
        assert user != null;
        user.setUsername(userModel.getUsername());
        user.setEmail(userModel.getEmail());
        user.setNames(userModel.getNames());
        user.setTel(userModel.getTel());
        User userOutput = this.userRepository.save(user);
        return this.modelMapper.map(userOutput, UserDetailsModel.class);
    }
}
