package com.travelsync.traveldatasync.model.reservation_history;

import com.travelsync.traveldatasync.model.notification.Notification;
import com.travelsync.traveldatasync.model.notification.NotificationService;
import com.travelsync.traveldatasync.model.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class ReservationHistoryService implements IReservationHistoryService {
    private final ReservationHistoryRepository reservationHistoryRepository;
    private final NotificationService notificationService;
    @Override
    public ReservationHistory addReservationHistory (ReservationHistory reservationHistory){
        return reservationHistoryRepository.save(reservationHistory);
    }

    @Override
    public void createReservationHistoryWithNotification(StringJoiner emailMessage, List<Trip> updatedTrips) {
        Notification notification = notificationService.createEmailNotification(emailMessage.toString());
        ReservationHistory reservationHistory = new ReservationHistory(notification, LocalDate.now());
        reservationHistory.addTrips(updatedTrips);
        notification.setReservationHistory(reservationHistory);
        addReservationHistory(reservationHistory);
    }
}
