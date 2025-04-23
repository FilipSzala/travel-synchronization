package com.travelsync.traveldatasync.model.reservation_history;

import com.travelsync.traveldatasync.model.notification.Notification;
import com.travelsync.traveldatasync.model.trip.Trip;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor

public class ReservationHistory {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany (cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable (
            name = "trip_reservationHistory",
            joinColumns = @JoinColumn (
                    name = "reservation_history_id",
                    foreignKey = @ForeignKey (name = "reservationHistory_trip_id_fk")
            ),
            inverseJoinColumns = @JoinColumn (
                    name = "trip_id",
                    foreignKey = @ForeignKey (name = "trip_reservationHistory_id_fk")
            )
    )
    private List <Trip> trips;
    @OneToOne (cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Notification notification;
    private LocalDate date;


    public ReservationHistory(Notification notification, LocalDate date) {
        this.notification = notification;
        this.date = date;
        this.trips = new ArrayList<>();
    }

    public void addTrips (List <Trip> trips){
        trips.stream()
                .forEach(trip -> {
                    this.trips.add(trip);
                    trip.getReservationHistories().add(this);
                });
    }
}
