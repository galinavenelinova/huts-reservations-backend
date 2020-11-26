package com.example.demo.web.controllers;

import com.example.demo.service.models.HutServiceModel;
import com.example.demo.service.services.HutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api", produces = "application/json")
public class HutController {
    private final HutService hutService;

    @Autowired
    public HutController(HutService hutService) {
        this.hutService = hutService;
    }

    @GetMapping("/{mountainId}/huts")
    public List<HutServiceModel> getHutsForMountain(@PathVariable String mountainId) {
        return hutService.getHutsForMountain(mountainId);
    }
}
