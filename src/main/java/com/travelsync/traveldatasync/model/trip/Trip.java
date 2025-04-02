package com.travelsync.traveldatasync.model.trip;

import com.travelsync.traveldatasync.model.booking.Booking;
import com.travelsync.traveldatasync.model.reservation_history.ReservationHistory;
import com.travelsync.traveldatasync.model.trip.trip_category.TripCategory;
import com.travelsync.traveldatasync.model.trip.trip_location.TripLocation;
import com.travelsync.traveldatasync.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int maxParticipantCount;
    private int currentParticipantCount;
    private BigDecimal price;
    @OneToOne ( mappedBy = "trip")
    private TripLocation location;
    @Enumerated
    private TripCategory tripCategory;
    @ManyToOne
    @JoinColumn (
            name = "user_id",
            foreignKey = @ForeignKey (name = "trip_user_fk")
    )
    private User user;
    @ManyToOne
    @JoinColumn (
            name = "reservvation_history_id",
            foreignKey = @ForeignKey (name = "reservation_history_fk")
    )
    private ReservationHistory reservationHistory;
    @OneToMany
    private List<Booking> bookings;
}
