package com.example.demo.service.services.impl;

import com.example.demo.data.models.Role;
import com.example.demo.data.repositories.RoleRepository;
import com.example.demo.service.models.RoleServiceModel;
import com.example.demo.service.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDB() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new Role("USER"));
            this.roleRepository.saveAndFlush(new Role("ADMIN"));
        }
    }

    @Override
    public Role finByAuthority(String role) {
        return this.roleRepository.findByAuthority(role);
    }

    @Override
    public List<Role> findAllRoles() {
        return this.roleRepository.findAll();
    }
}
