package com.travelsync.traveldatasync.model.trip.trip_location;

import com.travelsync.traveldatasync.model.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserId(Long id);
    List<Trip> findByUserIdAndStartDateGreaterThan(Long id, LocalDate startDate);
}