package com.example.demo.service.services.impl;

import com.example.demo.data.models.Hut;
import com.example.demo.data.models.Reservation;
import com.example.demo.data.models.User;
import com.example.demo.data.repositories.HutRepository;
import com.example.demo.data.repositories.ReservationRepository;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.service.services.ReservationService;
import com.example.demo.web.models.ReservationCheckModel;
import com.example.demo.web.models.ReservationModel;
import com.example.demo.web.models.UserDetailsModel;
import com.example.demo.web.models.UserInputModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {
    @MockBean
    ReservationRepository reservationRepository;

    @MockBean
    HutRepository hutRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    ReservationService reservationService;

    @Test
    void checkAvailability_whenPeopleCountBiggerThanBedCapacity_shouldReturnFalse() throws ParseException {
        Hut hut = new Hut();
        hut.setBedCapacity(20);
        hut.setReservations(new ArrayList<>());
        Mockito.when(hutRepository.findById("1")).thenReturn(Optional.of(hut));
        ReservationCheckModel reservationCheckModel = new ReservationCheckModel();
        reservationCheckModel.setPeopleCount(30);
        reservationCheckModel.setCheckinDate(new SimpleDateFormat("dd/MM/yyyy").parse("22/04/2021"));
        reservationCheckModel.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("24/04/2021"));

        assertFalse(reservationService.checkAvailability("1", reservationCheckModel));
    }

    @Test
    void checkAvailability_whenPeopleCountBiggerThanAvailableBedsForOneDate_shouldReturnFalse() throws ParseException {
        Hut hut = new Hut();
        hut.setBedCapacity(20);
        Reservation reservation = new Reservation();
        reservation.setCheckInDate(new SimpleDateFormat("dd/MM/yyyy").parse("22/04/2021"));
        reservation.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2021"));
        reservation.setPeopleCount(10);
        hut.setReservations(List.of(reservation));
        Mockito.when(hutRepository.findById("1")).thenReturn(Optional.of(hut));

        ReservationCheckModel reservationCheckModel1 = new ReservationCheckModel();
        reservationCheckModel1.setPeopleCount(11);
        reservationCheckModel1.setCheckinDate(new SimpleDateFormat("dd/MM/yyyy").parse("22/04/2021"));
        reservationCheckModel1.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("28/04/2021"));

        ReservationCheckModel reservationCheckModel2 = new ReservationCheckModel();
        reservationCheckModel2.setPeopleCount(11);
        reservationCheckModel2.setCheckinDate(new SimpleDateFormat("dd/MM/yyyy").parse("16/04/2021"));
        reservationCheckModel2.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2021"));

        assertFalse(reservationService.checkAvailability("1", reservationCheckModel1));
        assertFalse(reservationService.checkAvailability("1", reservationCheckModel2));
    }

    @Test
    void checkAvailability_whenPeopleCountBiggerThanAvailableBedsCalculatedFromMultipleReservations_shouldReturnFalse() throws ParseException {
        Hut hut = new Hut();
        hut.setBedCapacity(20);
        Reservation reservation1 = new Reservation();
        reservation1.setCheckInDate(new SimpleDateFormat("dd/MM/yyyy").parse("22/04/2021"));
        reservation1.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2021"));
        reservation1.setPeopleCount(5);
        Reservation reservation2 = new Reservation();
        reservation2.setCheckInDate(new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2021"));
        reservation2.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("25/04/2021"));
        reservation2.setPeopleCount(5);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        hut.setReservations(reservations);
        Mockito.when(hutRepository.findById("1")).thenReturn(Optional.of(hut));

        ReservationCheckModel reservationCheckModel1 = new ReservationCheckModel();
        reservationCheckModel1.setPeopleCount(11);
        reservationCheckModel1.setCheckinDate(new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2021"));
        reservationCheckModel1.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("30/04/2021"));

        assertFalse(reservationService.checkAvailability("1", reservationCheckModel1));
    }

    @Test
    void checkAvailability_whenPeopleCountEqualToAvailableBedsCalculatedFromMultipleReservations_shouldReturnTrue() throws ParseException {
        Hut hut = new Hut();
        hut.setBedCapacity(20);
        Reservation reservation1 = new Reservation();
        reservation1.setCheckInDate(new SimpleDateFormat("dd/MM/yyyy").parse("22/04/2021"));
        reservation1.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2021"));
        reservation1.setPeopleCount(5);
        Reservation reservation2 = new Reservation();
        reservation2.setCheckInDate(new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2021"));
        reservation2.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("25/04/2021"));
        reservation2.setPeopleCount(5);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        hut.setReservations(reservations);
        Mockito.when(hutRepository.findById("1")).thenReturn(Optional.of(hut));

        ReservationCheckModel reservationCheckModel1 = new ReservationCheckModel();
        reservationCheckModel1.setPeopleCount(10);
        reservationCheckModel1.setCheckinDate(new SimpleDateFormat("dd/MM/yyyy").parse("22/04/2021"));
        reservationCheckModel1.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2021"));

        assertTrue(reservationService.checkAvailability("1", reservationCheckModel1));
    }

    @Test
    void saveReservation_whenHutIdDoNotExist_shouldReturnFalse() {
        //hutRepository returns null as MockBean
        assertFalse(reservationService.saveReservation("1", new ReservationModel()));
    }

    @Test
    void saveReservation_whenHutIdExistsAndValidReservation_shouldReturnTrue() throws ParseException {
        Hut hut = new Hut();
        Mockito.when(hutRepository.findById("1")).thenReturn(Optional.of(hut));

        UserDetailsModel userDetailsModel = new UserDetailsModel();
        userDetailsModel.setId("1");
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setUser(userDetailsModel);
        reservationModel.setHut(hut);
        reservationModel.setPeopleCount(10);
        reservationModel.setCheckinDate(new SimpleDateFormat("dd/MM/yyyy").parse("22/04/2021"));
        reservationModel.setCheckoutDate(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2021"));
        User user = new User();
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));
        Reservation reservation = new Reservation();
        reservation.setId("1");
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(reservation);

        assertTrue(reservationService.saveReservation("1", reservationModel));
    }

    @Test
    void getReservationsForUser_whenUserIdDoNotExist_shouldReturnEmptyList() {
        //userRepository.findById() returns null as MockBean
        //reservationRepository.findByUser() returns null as MockBean
        assertEquals(new ArrayList<>(), reservationService.getReservationsForUser("1"));
    }

    @Test
    void getReservationsForUser_whenUserIdExistsAndUserHasReservations_shouldReturnListWithReservations() {

    }
}
