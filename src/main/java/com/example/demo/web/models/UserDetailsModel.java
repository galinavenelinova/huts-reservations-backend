package com.example.demo.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsModel {
    private String id;
    private String username;
    private String email;
    private String tel;
    private String names;
    private String role;
}
