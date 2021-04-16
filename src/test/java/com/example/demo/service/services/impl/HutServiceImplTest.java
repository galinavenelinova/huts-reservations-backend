package com.example.demo.service.services.impl;

import com.example.demo.data.models.Hut;
import com.example.demo.data.models.Mountain;
import com.example.demo.data.repositories.HutRepository;
import com.example.demo.data.repositories.MountainRepository;
import com.example.demo.service.services.HutService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HutServiceImplTest {
    ModelMapper modelMapper = new ModelMapper();
    HutRepository hutRepository = Mockito.mock(HutRepository.class);
    MountainRepository mountainRepository = Mockito.mock(MountainRepository.class);
    HutService hutService = new HutServiceImpl(hutRepository, mountainRepository, modelMapper);

    @Test
    void getHutsForMountain_whenNoHutsForMountain_shouldReturnEmptyList() {
        Mockito.when(hutRepository.findAllByMountainId("11")).thenReturn(new ArrayList<>());

        assertTrue(hutService.getHutsForMountain("11").isEmpty());
    }

    @Test
    void getHutsForMountain_whenOneHutForMountain_shouldReturnListWithOneHut() {
        Hut hut = new Hut();
        hut.setName("test");
        Mountain mountain = new Mountain("Рила", "инфо", List.of(hut));
        mountain.setId("11");
        hut.setMountain(mountain);
        Mockito.when(hutRepository.findAllByMountainId("11")).thenReturn(List.of(hut));

        assertEquals("test", hutService.getHutsForMountain("11").get(0).getName());
        assertEquals(1, hutService.getHutsForMountain("11").size());
    }

    @Test
    void getHutById_whenNoHutWithThisId_shouldReturnNull() {
        Mockito.when(hutRepository.findById("1")).thenReturn(Optional.empty());

        assertNull(hutService.getHutById("1"));
    }

    @Test
    void getHutById_whenHutWithThisId_shouldReturnThisHut() {
        Hut hut = new Hut();
        hut.setName("test1");
        hut.setId("1");
        Mockito.when(hutRepository.findById("1")).thenReturn(Optional.of(hut));

        assertEquals("test1", hutService.getHutById("1").getName());
    }
}
