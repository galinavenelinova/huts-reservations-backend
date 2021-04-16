package com.example.demo.service.services.impl;

import com.example.demo.data.models.Hut;
import com.example.demo.data.models.Mountain;
import com.example.demo.data.repositories.HutRepository;
import com.example.demo.data.repositories.MountainRepository;
import com.example.demo.service.models.HutServiceModel;
import com.example.demo.service.services.HutService;
import com.example.demo.web.models.HutInputModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HutServiceImpl implements HutService {
    private final String FILE_SAVE_PATH = "D:\\_softuni_\\14Angular\\huts-reservation-project\\huts-reservations-frontend\\src\\assets\\images\\huts\\";
    private final HutRepository hutRepository;
    private final MountainRepository mountainRepository;
    private final ModelMapper mapper;

    @Autowired
    public HutServiceImpl(HutRepository hutRepository, MountainRepository mountainRepository, ModelMapper mapper) {
        this.hutRepository = hutRepository;
        this.mountainRepository = mountainRepository;
        this.mapper = mapper;
    }

    @Override
    public List<HutServiceModel> getHutsForMountain(String mountainId) {
        return hutRepository.findAllByMountainId(mountainId).stream().map(m -> mapper.map(m, HutServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public HutServiceModel getHutById(String hutId) {
        return hutRepository.findById(hutId).map(
                m -> mapper.map(m, HutServiceModel.class)
        ).orElse(null);
    }

    @Override
    public HutServiceModel createHut(HutInputModel hutInputModel, String mountainId) {
        if (hutInputModel != null) {
            Hut hut = this.mapper.map(hutInputModel, Hut.class);
            Mountain mountain = this.mountainRepository.findById(mountainId).orElse(null);
            hut.setMountain(mountain);
            hut = this.hutRepository.save(hut);
            return this.mapper.map(hut, HutServiceModel.class);
        } else {
            return null;
        }
    }

    @Override
    public void saveHutImage(MultipartFile imageFile, String hutId) throws IOException {
        String filePath = FILE_SAVE_PATH + hutId + ".jpg";
        File imageToSave = new File(filePath);
        imageFile.transferTo(imageToSave);
    }

}
