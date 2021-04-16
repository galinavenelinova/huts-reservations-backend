package com.example.demo.helpers;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateService {
    public Date getCurrentDate() {
        return new Date();
    }
}
