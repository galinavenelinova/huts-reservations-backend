package com.example.demo.service.services.impl;

import com.example.demo.data.models.Hut;
import com.example.demo.data.models.Reservation;
import com.example.demo.data.repositories.HutRepository;
import com.example.demo.data.repositories.ReservationRepository;
import com.example.demo.service.services.ReservationService;
import com.example.demo.web.models.ReservationModel;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final HutRepository hutRepository;
    private final ModelMapper modelmapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, HutRepository hutRepository, ModelMapper modelmapper) {
        this.reservationRepository = reservationRepository;
        this.hutRepository = hutRepository;
        this.modelmapper = modelmapper;
    }

    @Override
    public boolean checkAvailability(String hutId) {
        System.out.println(hutId);
        return true;
    }

    @Override
    public ReservationModel saveReservation(String hutId, ReservationModel reservationModel) {
        Reservation reservation = this.modelmapper.map(reservationModel, Reservation.class);
        Hut hut = this.hutRepository.findById(hutId).orElse(null);
        reservation.setHut(hut);
        Reservation reservationOutput = this.reservationRepository.save(reservation);
        return this.modelmapper.map(reservationOutput, ReservationModel.class);
    }
}
