package com.example.demo.data.models;

import com.example.demo.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "huts")
@Getter
@Setter
@NoArgsConstructor
public class Hut extends BaseEntity {
    @Column
    private String name;

    @Column
    private String shortInfo;

    @Column
    private String longInfo;

    @ManyToOne
    private Mountain mountain;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hut")
    private List<Reservation> reservations;
}
