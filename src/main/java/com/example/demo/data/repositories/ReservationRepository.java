package com.example.demo.data.repositories;

import com.example.demo.data.models.Reservation;
import com.example.demo.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByUser(User user);
}
