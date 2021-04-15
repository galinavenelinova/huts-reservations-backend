package com.example.demo.service.services.impl;

import com.example.demo.data.models.Hut;
import com.example.demo.data.models.Reservation;
import com.example.demo.data.models.User;
import com.example.demo.data.repositories.HutRepository;
import com.example.demo.data.repositories.ReservationRepository;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.service.models.ReservationServiceModel;
import com.example.demo.service.services.ReservationService;
import com.example.demo.web.models.ReservationCheckModel;
import com.example.demo.web.models.ReservationListModel;
import com.example.demo.web.models.ReservationModel;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Calendar;
import java.util.Date;
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
    public Boolean checkAvailability(String hutId, ReservationCheckModel reservationCheckModel) {
        boolean isAvailable = true;
        Hut hut = this.hutRepository.findById(hutId).orElse(null);
        if (hut != null) {
            Integer bedCapacity = hut.getBedCapacity();
            List<ReservationServiceModel> reservations = hut.getReservations()
                    .stream()
                    .map(r -> this.modelmapper.map(r, ReservationServiceModel.class))
                    .collect(Collectors.toList());
            Calendar current = Calendar.getInstance();
            current.setTime(reservationCheckModel.getCheckinDate());

            while (!current.getTime().after(reservationCheckModel.getCheckoutDate())) {
                int bookedBedsForDate = 0;
                for (var reservation : reservations) {
                    Date reservationCheckinDate = reservation.getCheckInDate();
                    Date reservationCheckoutDate = reservation.getCheckoutDate();
                    if ((current.getTime().equals(reservationCheckinDate) || current.getTime().after(reservationCheckinDate)) && current.getTime().before(reservationCheckoutDate)){
                        bookedBedsForDate += reservation.getPeopleCount();
                    }
                }
                if (reservationCheckModel.getPeopleCount() > bedCapacity - bookedBedsForDate) {
                    isAvailable = false;
                }

                current.add(Calendar.DATE, 1);
            }
        }

        return isAvailable;
    }

    @Override
    public Boolean saveReservation(String hutId, ReservationModel reservationModel) {
        Reservation reservation = this.modelmapper.map(reservationModel, Reservation.class);
        Hut hut = this.hutRepository.findById(hutId).orElse(null);
        if (hut != null && reservationModel.getUser() != null) {
            User user = this.userRepository.findById(reservationModel.getUser().getId()).orElse(null);
            if (user != null) {
                reservation.setHut(hut);
                reservation.setUser(user);
                Reservation reservationOutput = this.reservationRepository.save(reservation);
                return !reservationOutput.getId().isEmpty();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<ReservationListModel> getReservationsForUser(String userId) {
        User user = this.userRepository.findById(userId).orElse(null);
        List<Reservation> reservations = this.reservationRepository.findByUser(user);
        return reservations.stream().map(r -> this.modelmapper.map(r, ReservationListModel.class)).collect(Collectors.toList());
    }

    @Override
    public boolean deleteReservation(String reservationId) {
        Reservation reservation = this.reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null) {
            this.reservationRepository.delete(reservation);
            return true;
        } else {
            return false;
        }
    }
}
