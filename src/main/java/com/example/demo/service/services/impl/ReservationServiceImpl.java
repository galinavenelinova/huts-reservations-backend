package com.example.demo.service.services.impl;

import com.example.demo.data.models.Hut;
import com.example.demo.data.models.Reservation;
import com.example.demo.data.models.User;
import com.example.demo.data.repositories.HutRepository;
import com.example.demo.data.repositories.ReservationRepository;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.service.services.ReservationService;
import com.example.demo.web.models.ReservationListModel;
import com.example.demo.web.models.ReservationModel;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final HutRepository hutRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelmapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, HutRepository hutRepository, UserRepository userRepository, ModelMapper modelmapper) {
        this.reservationRepository = reservationRepository;
        this.hutRepository = hutRepository;
        this.userRepository = userRepository;
        this.modelmapper = modelmapper;
    }

    @Override
    public boolean checkAvailability(String hutId) {
        System.out.println(hutId);
        return true;
    }

    @Override
    public Boolean saveReservation(String hutId, ReservationModel reservationModel) {
        Reservation reservation = this.modelmapper.map(reservationModel, Reservation.class);
        Hut hut = this.hutRepository.findById(hutId).orElse(null);
        User user = this.userRepository.findById(reservationModel.getUser().getId()).orElse(null);
        reservation.setHut(hut);
        reservation.setUser(user);
        Reservation reservationOutput = this.reservationRepository.save(reservation);
        return !reservationOutput.getId().isEmpty();
    }

    @Override
    public List<ReservationListModel> loadReservationsForUser(String userId) {
        User user = this.userRepository.findById(userId).orElse(null);
        List<Reservation> reservations = this.reservationRepository.findByUser(user);
        return reservations.stream().map(r -> this.modelmapper.map(r, ReservationListModel.class)).collect(Collectors.toList());
    }
}
