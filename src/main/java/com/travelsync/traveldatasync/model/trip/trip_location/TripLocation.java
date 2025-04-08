package com.travelsync.traveldatasync.model.trip.trip_location;

import com.travelsync.traveldatasync.model.trip.Trip;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TripLocation {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    //TODO: change it to Enum with all countries in the world
    private String country;
    private String city;
    private String region;
    @OneToOne
    @JoinColumn (
            name = "trip_id",
            foreignKey = @ForeignKey
                    (name = "tripLocation_trip_fk")
    )
    private Trip trip;

}
