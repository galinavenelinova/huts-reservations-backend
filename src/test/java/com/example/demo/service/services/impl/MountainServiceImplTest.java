package com.example.demo.service.services.impl;

import com.example.demo.data.models.Mountain;
import com.example.demo.data.repositories.MountainRepository;
import com.example.demo.service.services.MountainService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MountainServiceImplTest {
    ModelMapper modelMapper = new ModelMapper();
    MountainRepository mountainRepository = Mockito.mock(MountainRepository.class);
    MountainService mountainService = new MountainServiceImpl(mountainRepository, modelMapper);

    @Test
    void getAll_whenThreeMountains_shouldReturnListWithThreeMountains() {
        Mountain mountain1 = new Mountain();
        Mountain mountain2 = new Mountain();
        Mountain mountain3 = new Mountain();
        Mockito.when(mountainRepository.findAll()).thenReturn(List.of(mountain1, mountain2, mountain3));

        assertEquals(3, mountainService.getAll().size());
    }

    @Test
    void getAll_whenNoMountains_shouldReturnEmptyList() {
        Mockito.when(mountainRepository.findAll()).thenReturn(new ArrayList<>());

        assertTrue(mountainService.getAll().isEmpty());
    }
}
