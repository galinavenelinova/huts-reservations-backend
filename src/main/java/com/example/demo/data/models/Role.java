package com.example.demo.data.models;

import com.example.demo.data.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity implements GrantedAuthority {
    private String authority;
}
