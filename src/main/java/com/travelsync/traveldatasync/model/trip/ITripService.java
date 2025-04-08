package com.travelsync.traveldatasync.model.trip;

import com.travelsync.traveldatasync.model.trip.request.AddTripRequest;
import com.travelsync.traveldatasync.model.trip.request.UpdateTripRequest;

import java.util.List;

public interface ITripService {
    Trip addTrip (AddTripRequest tripRequest);

    Trip findById(Long tripId);

    Trip updateTrip(Long tripId, UpdateTripRequest updateTrip);

    void deleteTrip (Long tripId);

    List <Trip> findAllTrips();

    List<Trip> findAllTripByUserId (Long userId);
    List<Trip> findUpcomingTripsByUserId(Long userId);

}
