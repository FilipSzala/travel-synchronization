package com.travelsync.traveldatasync.model.user;

import com.travelsync.traveldatasync.model.notification.Notification;
import com.travelsync.traveldatasync.model.trip.Trip;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String companyName;
    private String phoneNumber;
    @NaturalId
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @OneToMany (
            mappedBy = "user"
    )
    private List <Trip> trips;
    @OneToMany (
            mappedBy = "user"
    )
    private List <Notification> notifications;
}
