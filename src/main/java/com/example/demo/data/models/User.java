package com.example.demo.data.models;

import com.example.demo.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String names;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Reservation> reservations;

    @OneToMany
    private List<Role> authorities;
}
