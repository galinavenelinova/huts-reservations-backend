package com.example.demo.web.controllers;

import com.example.demo.service.services.ReservationService;
import com.example.demo.web.models.ReservationCheckModel;
import com.example.demo.web.models.ReservationListModel;
import com.example.demo.web.models.ReservationModel;
import com.example.demo.web.models.UserDetailsModel;
import com.google.gson.Gson;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
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
    public boolean checkAvailability(@PathVariable String hutId, @RequestBody ReservationModel reservationModel) {
        return true;
//        return this.reservationService.checkAvailability(hutId, reservationModel);
    }

    @PostMapping("/huts/{hutId}/reservation")
    public ResponseEntity<Boolean> saveReservation(@PathVariable String hutId, @RequestBody ReservationModel reservationModel) {
        Boolean savedSuccessfully = this.reservationService.saveReservation(hutId, reservationModel);
        return new ResponseEntity<Boolean>(savedSuccessfully, HttpStatus.OK);
    }

    @GetMapping("/reservation/{userId}")
    public ResponseEntity<List<ReservationListModel>> getReservationsForUser(@PathVariable String userId) {
        List<ReservationListModel> reservationListModels = this.reservationService.loadReservationsForUser(userId);
        return new ResponseEntity<>(reservationListModels, HttpStatus.OK);
    }
}
