package com.travelsync.traveldatasync.model.booking;

import com.travelsync.traveldatasync.model.trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int participantCount;

    @ManyToOne
    @JoinColumn ( name = "trip_id", foreignKey = @ForeignKey (name = "booking_trip_fk"))
    private Trip trip;
}
