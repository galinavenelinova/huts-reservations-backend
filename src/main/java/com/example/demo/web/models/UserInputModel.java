package com.example.demo.web.models;

import com.example.demo.data.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserInputModel {
    private String username;
    private String email;
    private String tel;
    private String names;
    private String password;
    private List<Role> authorities;
}
