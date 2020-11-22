package com.example.demo.data.repositories;

import com.example.demo.data.models.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MountainRepository extends JpaRepository<Mountain, String> {
}
