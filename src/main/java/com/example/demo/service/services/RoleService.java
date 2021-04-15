package com.example.demo.service.services;

import com.example.demo.data.models.Role;
import com.example.demo.service.models.RoleServiceModel;

import java.util.List;
import java.util.Set;

public interface RoleService {
    void seedRolesInDB();

    Role findByAuthority(String role);

    List<Role> findAllRoles();

    void makeUserAdmin(String username);
}
