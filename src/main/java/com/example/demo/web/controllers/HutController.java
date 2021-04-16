package com.example.demo.web.controllers;

import com.example.demo.service.models.HutServiceModel;
import com.example.demo.service.services.HutService;
import com.example.demo.web.models.HutInputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/huts/{hutId}")
    public HutServiceModel getHutById(@PathVariable String hutId) {
        return hutService.getHutById(hutId);
    }

    @PostMapping("/huts/create")
    public HutServiceModel createHut(@RequestBody HutInputModel hutInputModel, @RequestParam String mountainId) throws IOException {
        return hutService.createHut(hutInputModel, mountainId);
    }

    @PostMapping("/huts/{hutId}/image-upload")
    public void uploadImageForHut(@PathVariable String hutId, @RequestParam("file") MultipartFile imageFile) throws IOException {
        hutService.saveHutImage(imageFile, hutId);
    }
}
