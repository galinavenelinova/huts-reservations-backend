package com.example.demo.service.services;

import com.example.demo.service.models.HutServiceModel;
import com.example.demo.service.models.MountainServiceModel;
import com.example.demo.web.models.HutInputModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HutService {
    List<HutServiceModel> getHutsForMountain(String mountainId);

    HutServiceModel getHutById(String hutId);

    void saveHutImage(MultipartFile imageFile, String hutId) throws IOException;

    HutServiceModel createHut(HutInputModel hutInputModel, String mountainId);
}
