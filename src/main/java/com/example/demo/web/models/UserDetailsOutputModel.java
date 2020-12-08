package com.example.demo.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsOutputModel {
    private String username;
    private String email;
    private String password;
    private String tel;
    private String names;
}
