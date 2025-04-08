package com.travelsync.traveldatasync.model.trip.request;

import lombok.Data;

@Data
public class TripAvailabilityRequest {
    private Long tripId;
    private int currentParticipantCount;
}
