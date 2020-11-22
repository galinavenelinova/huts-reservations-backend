package com.example.demo.service.services;

import com.example.demo.service.models.MountainServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MountainService {
    List<MountainServiceModel> getAll();
}
