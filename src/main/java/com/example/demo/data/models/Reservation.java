package com.example.demo.data.models;

import com.example.demo.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation extends BaseEntity {
    @Column(nullable = false)
    private Date checkInDate;

    @Column(nullable = false)
    private Date checkoutDate;

    @Column(nullable = false)
    private Integer peopleCount;

    @ManyToOne
    private Hut hut;

    @ManyToOne
    private User user;
}
