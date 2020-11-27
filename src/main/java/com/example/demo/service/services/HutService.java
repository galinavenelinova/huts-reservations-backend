package com.example.demo.service.services;

import com.example.demo.service.models.HutServiceModel;
import com.example.demo.service.models.MountainServiceModel;

import java.util.List;

public interface HutService {
    List<HutServiceModel> getAll();

    List<HutServiceModel> getHutsForMountain(String mountainId);

    HutServiceModel getHutById(String hutId);
}
