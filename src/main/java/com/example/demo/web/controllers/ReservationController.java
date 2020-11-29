package com.example.demo.web.controllers;

import com.example.demo.service.services.ReservationService;
import com.example.demo.web.models.ReservationCheckModel;
import com.google.gson.Gson;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
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
}
