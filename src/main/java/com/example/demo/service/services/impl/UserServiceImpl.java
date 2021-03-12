package com.example.demo.service.services.impl;

import com.example.demo.data.models.Role;
import com.example.demo.data.models.User;
import com.example.demo.data.repositories.RoleRepository;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.service.services.RoleService;
import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserDetailsModel;
import com.example.demo.web.models.UserInputModel;
import com.example.demo.web.models.UserOutputModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        Set<Role> authorities = user.getAuthorities();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public UserOutputModel register(UserInputModel userServiceModel) {
        this.roleService.seedRolesInDB();

        User user = this.modelMapper.map(userServiceModel, User.class);
        if (this.userRepository.count() == 0) {
            user.setAuthorities(new LinkedHashSet<>(this.roleRepository.findAll()));
        } else {
            LinkedHashSet<Role> roles = new LinkedHashSet<>();
            roles.add(this.roleRepository.findByAuthority("USER"));
            user.setAuthorities(roles);
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        this.userRepository.saveAndFlush(user);
        return this.modelMapper.map(user, UserOutputModel.class);
    }

    @Override
    public UserOutputModel login(UserInputModel userModel) {
        User user = this.userRepository.findByUsername(userModel.getUsername()).orElse(null);
        if(user == null || !bCryptPasswordEncoder.matches(userModel.getPassword(), user.getPassword())) {
            return null;
        }else {
            return this.modelMapper.map(user, UserOutputModel.class);
        }
    }

    @Override
    public UserDetailsModel getUserDetails(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
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
