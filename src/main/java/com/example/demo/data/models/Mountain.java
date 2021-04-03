package com.example.demo.data.models;

import com.example.demo.data.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mountains")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mountain extends BaseEntity {
    @Column
    private String name;

    @Column(columnDefinition = "text")
    private String info;

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mountain")
    private List<Hut> huts;
}
