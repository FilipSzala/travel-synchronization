package com.travelsync.traveldatasync.model.reservation_history;

import com.travelsync.traveldatasync.model.trip.Trip;

import java.util.List;
import java.util.StringJoiner;

public interface IReservationHistoryService {
    ReservationHistory addReservationHistory (ReservationHistory reservationHistory);

    void createReservationHistoryWithNotification(StringJoiner emailMessage, List<Trip> updatedTrips);
}
