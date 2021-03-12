package com.example.demo.service.services;

import com.example.demo.service.models.RoleServiceModel;

import java.util.Set;

public interface RoleService {
    void seedRolesInDB();

    RoleServiceModel finByAuthority(String role);

    Set<RoleServiceModel> findAllRoles();
}
