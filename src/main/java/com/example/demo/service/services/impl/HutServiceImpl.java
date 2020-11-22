package com.example.demo.service.services.impl;

import com.example.demo.data.repositories.HutRepository;
import com.example.demo.data.repositories.MountainRepository;
import com.example.demo.service.models.HutServiceModel;
import com.example.demo.service.models.MountainServiceModel;
import com.example.demo.service.services.HutService;
import com.example.demo.service.services.MountainService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HutServiceImpl implements HutService {
    private final HutRepository hutRepository;
    private final ModelMapper mapper;

    @Autowired
    public HutServiceImpl(HutRepository hutRepository, ModelMapper mapper) {
        this.hutRepository = hutRepository;
        this.mapper = mapper;
    }

    @Override
    public List<HutServiceModel> getAll() {
        return hutRepository.findAll().stream().map(m -> mapper.map(m, HutServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<HutServiceModel> getHutsForMountain(String mountainId) {
        return hutRepository.findAllByMountainId(mountainId).stream().map(m -> mapper.map(m, HutServiceModel.class))
                .collect(Collectors.toList());
    }
}
