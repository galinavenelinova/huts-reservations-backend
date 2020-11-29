package com.example.demo.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
}
