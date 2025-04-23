package com.travelsync.traveldatasync.model.trip.request.wrapper;

import com.travelsync.traveldatasync.model.trip.request.TripAvailabilityRequest;
import lombok.Data;

import java.util.List;

@Data
public class TripAvailabilityRequestWrapper {
    private Long tourOperatorId;
    private List<TripAvailabilityRequest> fetchedTrips;
    private List<TripAvailabilityRequest> tripsRequest;
}
