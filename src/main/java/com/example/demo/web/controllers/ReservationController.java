package com.example.demo.web.controllers;

import com.example.demo.service.services.ReservationService;
import com.example.demo.web.models.ReservationCheckModel;
import com.example.demo.web.models.ReservationListModel;
import com.example.demo.web.models.ReservationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api", consumes = "application/json")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/huts/{hutId}/check")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable String hutId, @RequestBody ReservationCheckModel reservationCheckModel) {
        Boolean isAvailable = this.reservationService.checkAvailability(hutId, reservationCheckModel);
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }

    @PostMapping("/huts/{hutId}/reservation")
    public ResponseEntity<Boolean> saveReservation(@PathVariable String hutId, @RequestBody ReservationModel reservationModel) {
        Boolean savedSuccessfully = this.reservationService.saveReservation(hutId, reservationModel);
        return new ResponseEntity<>(savedSuccessfully, HttpStatus.OK);
    }

    @GetMapping("/reservation/{userId}")
    public ResponseEntity<List<ReservationListModel>> getReservationsForUser(@PathVariable String userId) {
        List<ReservationListModel> reservationListModels = this.reservationService.getReservationsForUser(userId);
        return new ResponseEntity<>(reservationListModels, HttpStatus.OK);
    }
}
