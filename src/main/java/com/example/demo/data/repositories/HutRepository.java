package com.example.demo.data.repositories;

import com.example.demo.data.models.Hut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HutRepository extends JpaRepository<Hut, String> {
    List<Hut> findAllByMountainId(String mountainId);
}
