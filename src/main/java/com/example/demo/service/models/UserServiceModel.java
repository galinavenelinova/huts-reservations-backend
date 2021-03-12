package com.example.demo.service.models;

import com.example.demo.data.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel {
    private String username;
    private String email;
    private String password;
    private String tel;
    private String names;
    private Set<RoleServiceModel> authorities;
}
