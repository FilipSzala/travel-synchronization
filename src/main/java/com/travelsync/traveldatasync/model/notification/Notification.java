package com.travelsync.traveldatasync.model.notification;
import com.travelsync.traveldatasync.model.notification.notification_status.NotificationStatus;
import com.travelsync.traveldatasync.model.notification.notification_type.NotificationType;
import com.travelsync.traveldatasync.model.reservation_history.ReservationHistory;
import com.travelsync.traveldatasync.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Notification {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne (cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn (name = "user_id",
                    foreignKey = @ForeignKey (
                            name = "reservation_history_user_fk"
                    ))
    private User user;

    @OneToOne
    @JoinColumn (name = "reservation_history_id",
    foreignKey = @ForeignKey (name = "notification_reservation_history_id"))
    private ReservationHistory reservationHistory;
    private String message;
    @Enumerated
    private NotificationStatus notificationStatus;
    @Enumerated
    private NotificationType notificationType;

}
