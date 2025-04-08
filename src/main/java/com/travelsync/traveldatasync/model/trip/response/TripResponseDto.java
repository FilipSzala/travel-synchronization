package com.travelsync.traveldatasync.model.trip.response;

import com.travelsync.traveldatasync.model.trip.trip_category.TripCategory;
import com.travelsync.traveldatasync.model.trip.trip_location.TripLocation;
import com.travelsync.traveldatasync.model.trip.trip_location.response.LocationResponseDto;
import com.travelsync.traveldatasync.model.user.response.UserResponseDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TripResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxParticipantCount;
    private int currentParticipantCount;
    private BigDecimal price;
    private LocationResponseDto location;
    private TripCategory tripCategory;
    private UserResponseDto user;
}
