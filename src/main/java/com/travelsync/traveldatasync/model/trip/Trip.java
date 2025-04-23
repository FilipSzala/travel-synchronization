package com.travelsync.traveldatasync.model.trip;
import com.travelsync.traveldatasync.model.booking.Booking;
import com.travelsync.traveldatasync.model.reservation_history.ReservationHistory;
import com.travelsync.traveldatasync.model.trip.trip_category.TripCategory;
import com.travelsync.traveldatasync.model.trip.trip_location.TripLocation;
import com.travelsync.traveldatasync.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxParticipantCount;
    private int currentParticipantCount;
    private BigDecimal price;
    @OneToOne ( mappedBy = "trip", cascade = CascadeType.ALL)
    private TripLocation location;
    @Enumerated
    private TripCategory tripCategory;
    @ManyToOne
    @JoinColumn (
            name = "user_id",
            foreignKey = @ForeignKey (name = "trip_user_fk")
    )
    private User user;
    @ManyToMany (
            mappedBy = "trips"
    )
    private List <ReservationHistory> reservationHistories;
    @OneToMany
    private List<Booking> bookings;
}
