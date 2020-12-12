package com.example.demo.service.services;

import com.example.demo.web.models.ReservationListModel;
import com.example.demo.web.models.ReservationModel;

import java.util.Date;
import java.util.List;

public interface ReservationService {
    boolean checkAvailability(String hutId);

    Boolean saveReservation(String hutId, ReservationModel reservationModel);

    List<ReservationListModel> loadReservationsForUser(String userId);
}
