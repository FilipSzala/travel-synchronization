package com.travelsync.traveldatasync.model.trip.request;

import com.travelsync.traveldatasync.model.trip.trip_category.TripCategory;
import com.travelsync.traveldatasync.model.trip.trip_location.TripLocation;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AddTripRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxParticipantCount;
    private int currentParticipantCount;
    private BigDecimal price;
    private TripLocation location;
    private TripCategory tripCategory;
    private String userCompanyName;
}
