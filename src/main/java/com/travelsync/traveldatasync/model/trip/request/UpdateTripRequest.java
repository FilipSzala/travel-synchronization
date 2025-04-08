package com.travelsync.traveldatasync.model.trip.request;

import com.travelsync.traveldatasync.model.trip.trip_category.TripCategory;
import com.travelsync.traveldatasync.model.trip.trip_location.TripLocation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class UpdateTripRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxParticipantCount;
    private int currentParticipantCount;
    private BigDecimal price;
    private TripLocation location;
    private TripCategory tripCategory;
    private String userFullName;

    public boolean hasEmptyField() {
        return
                title == null ||
                title.isEmpty() ||
                description == null ||
                description.isEmpty() ||
                startDate == null ||
                endDate == null ||
                maxParticipantCount <= 0 ||
                currentParticipantCount < 0 ||
                price == null ||
                location == null ||
                tripCategory == null ||
                userFullName == null ||
                userFullName.isEmpty();
    }
}


