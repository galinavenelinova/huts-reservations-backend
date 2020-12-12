package com.example.demo.service.services;

import com.example.demo.web.models.ReservationModel;

import java.util.Date;

public interface ReservationService {
    boolean checkAvailability(String hutId);

    ReservationModel saveReservation(String hutId, ReservationModel reservationModel);
}
