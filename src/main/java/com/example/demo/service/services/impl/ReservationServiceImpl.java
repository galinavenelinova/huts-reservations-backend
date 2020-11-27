package com.example.demo.service.services.impl;

import com.example.demo.service.services.ReservationService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Override
    public boolean checkAvailability(String hutId) {
        System.out.println(hutId);
        return true;
    }
}
