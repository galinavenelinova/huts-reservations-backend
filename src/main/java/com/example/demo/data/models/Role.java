package com.example.demo.data.models;

import com.example.demo.data.models.base.BaseEntity;
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
@Setter
@NoArgsConstructor
public class Role extends BaseEntity implements GrantedAuthority {
    @Column
    private String authority;

    @ManyToOne
    private User user;

    public void setAuthority() {
        this.authority = "ROLE_USER";
    }
}
