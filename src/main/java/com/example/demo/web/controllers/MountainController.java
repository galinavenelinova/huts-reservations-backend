package com.example.demo.web.controllers;

import com.example.demo.service.models.MountainServiceModel;
import com.example.demo.service.services.MountainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/mountains", produces = "application/json")
public class MountainController {
    private final MountainService mountainService;

    @Autowired
    public MountainController(MountainService mountainService) {
        this.mountainService = mountainService;
    }

    @GetMapping("")
    public List<MountainServiceModel> getAllMountains() {
        return mountainService.getAll();
    }
}
