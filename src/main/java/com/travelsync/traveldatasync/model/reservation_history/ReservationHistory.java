package com.travelsync.traveldatasync.model.reservation_history;

import com.travelsync.traveldatasync.model.notification.Notification;
import com.travelsync.traveldatasync.model.trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor

public class ReservationHistory {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany (mappedBy = "reservationHistory")
    private List<Trip> trips;
    @OneToOne
    private Notification notification;
    private Date date;
}
