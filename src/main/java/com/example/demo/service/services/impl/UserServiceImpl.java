package com.example.demo.service.services.impl;

import com.example.demo.config.exceptions.InvalidUserException;
import com.example.demo.data.models.Role;
import com.example.demo.data.models.User;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.service.services.RoleService;
import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserDetailsModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            List<Role> authorities = user.getAuthorities();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities
            );
        } else {
            return null;
        }
    }

    @Override
    public UserOutputModel register(UserInputModel userServiceModel) throws InvalidUserException {
        this.roleService.seedRolesInDB();

        User user = this.modelMapper.map(userServiceModel, User.class);
        if (this.userRepository.count() == 0) {
            user.setAuthorities(new ArrayList<>(this.roleService.findAllRoles()));
        } else {
            List<Role> roles = new ArrayList<>();
            roles.add(this.roleService.finByAuthority("USER"));
            user.setAuthorities(roles);
        }

        if (userServiceModel.getPassword() != null && userServiceModel.getUsername() != null &&
                userServiceModel.getEmail() != null && userServiceModel.getNames() != null &&
                userServiceModel.getTel() != null) {
            if (userServiceModel.getPassword().length() >= 5 && userServiceModel.getUsername().length() >= 5) {
                user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
                user = this.userRepository.saveAndFlush(user);
            } else {
                throw new InvalidUserException("Invalid length of password or username!");
            }
        } else {
            throw new InvalidUserException("One or more not nullable field(s) of User is null (password, username, email, names, tel)!");
        }

        return this.modelMapper.map(user, UserOutputModel.class);
    }

    @Override
    public UserDetailsModel getUserDetails(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        return this.modelMapper.map(user, UserDetailsModel.class);
    }

    @Override
    public UserDetailsModel updateUserDetails(String username, UserInputModel userModel) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        assert user != null;
        user.setUsername(userModel.getUsername());
        user.setEmail(userModel.getEmail());
        user.setNames(userModel.getNames());
        user.setTel(userModel.getTel());
        User userOutput = this.userRepository.save(user);
        return this.modelMapper.map(userOutput, UserDetailsModel.class);
    }

    @Override
    public List<UserOutputModel> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> new UserOutputModel(
                        u.getUsername(),
                        u.getAuthorities().get(u.getAuthorities().size() - 1).getAuthority()))
                .collect(Collectors.toList());

    }
}
