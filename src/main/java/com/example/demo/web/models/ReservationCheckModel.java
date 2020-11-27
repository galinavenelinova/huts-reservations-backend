package com.example.demo.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReservationCheckModel {
    private Date checkinDate;
    private Date checkoutDate;
    private Integer peopleCount;
}
