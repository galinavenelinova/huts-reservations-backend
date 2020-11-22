package com.example.demo.service.services.impl;

import com.example.demo.data.repositories.MountainRepository;
import com.example.demo.service.models.MountainServiceModel;
import com.example.demo.service.services.MountainService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MountainServiceImpl implements MountainService {
    private final MountainRepository mountainRepository;
    private final ModelMapper mapper;

    @Autowired
    public MountainServiceImpl(MountainRepository mountainRepository, ModelMapper mapper) {
        this.mountainRepository = mountainRepository;
        this.mapper = mapper;
    }

    @Override
    public List<MountainServiceModel> getAll() {
        return mountainRepository.findAll().stream().map(m -> mapper.map(m, MountainServiceModel.class))
                .collect(Collectors.toList());
    }
}
