package com.example.demo.service.services;

import com.example.demo.web.models.ReservationCheckModel;
import com.example.demo.web.models.ReservationListModel;
import com.example.demo.web.models.ReservationModel;
import java.util.List;

public interface ReservationService {
    Boolean checkAvailability(String hutId, ReservationCheckModel reservationCheckModel);

    Boolean saveReservation(String hutId, ReservationModel reservationModel);

    List<ReservationListModel> getReservationsForUser(String userId);

    boolean deleteReservation(String reservationId);
}
