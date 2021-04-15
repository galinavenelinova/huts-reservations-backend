package com.example.demo.service.services.impl;

import com.example.demo.data.models.Role;
import com.example.demo.data.models.User;
import com.example.demo.data.repositories.RoleRepository;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.service.services.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void seedRolesInDB() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new Role("USER"));
            this.roleRepository.saveAndFlush(new Role("ADMIN"));
        }
    }

    @Override
    public Role findByAuthority(String role) {
        return this.roleRepository.findByAuthority(role);
    }

    @Override
    public List<Role> findAllRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void makeUserAdmin(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            List<Role> roles = user.getAuthorities();
            roles.add(this.roleRepository.findByAuthority("ADMIN"));
            user.setAuthorities(roles);
            this.userRepository.save(user);
        }
    }
}
