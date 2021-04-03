package com.example.demo.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReservationServiceModel {
    private Date checkInDate;
    private Date checkoutDate;
    private Integer peopleCount;
}
