package com.indigo.repository;

import com.indigo.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightCentralRepository extends JpaRepository<Passenger, String> {
}
