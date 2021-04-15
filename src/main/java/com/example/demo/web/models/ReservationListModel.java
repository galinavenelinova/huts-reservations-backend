package com.example.demo.web.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReservationListModel {
    private String id;
    private Date checkinDate;
    private Date checkoutDate;
    private Integer peopleCount;
    private String hutName;
}
