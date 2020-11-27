package com.example.demo.web.controllers;

import com.example.demo.service.services.ReservationService;
import com.example.demo.web.models.ReservationCheckModel;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value = "/huts/{hutId}/check", consumes = "application/json")
    public boolean checkAvailability(@PathVariable String hutId, @RequestBody RequestBody body) {
        System.out.println("post 1 request received");
        System.out.println(body);
        return false;
//        return this.reservationService.checkAvailability(hutId);
    }
}
