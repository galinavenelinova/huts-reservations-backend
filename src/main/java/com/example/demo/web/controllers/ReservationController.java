package com.example.demo.web.controllers;

import com.example.demo.service.services.ReservationService;
import com.example.demo.web.models.ReservationCheckModel;
import com.example.demo.web.models.ReservationModel;
import com.example.demo.web.models.UserDetailsModel;
import com.google.gson.Gson;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api", produces = "application/json")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/huts/{hutId}/check")
    public boolean checkAvailability(@PathVariable String hutId, @RequestBody String body) {
        ReservationCheckModel rModel = new Gson().fromJson(body, ReservationCheckModel.class);
        return true;
//        return this.reservationService.checkAvailability(hutId, rModel);
    }

    @PostMapping("/huts/{hutId}/reservation")
    public ResponseEntity<ReservationModel> saveReservation(@PathVariable String hutId, @RequestBody ReservationModel reservationModel) {
        ReservationModel reservationOutputModel = this.reservationService.saveReservation(hutId, reservationModel);
        return new ResponseEntity<>(reservationOutputModel, HttpStatus.OK);
    }
}
