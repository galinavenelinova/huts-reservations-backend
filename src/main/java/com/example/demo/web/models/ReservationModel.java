package com.example.demo.web.models;

import com.example.demo.data.models.Hut;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReservationModel {
    private Date checkinDate;
    private Date checkoutDate;
    private Integer peopleCount;
    private Hut hut;
    private UserDetailsModel user;
}
