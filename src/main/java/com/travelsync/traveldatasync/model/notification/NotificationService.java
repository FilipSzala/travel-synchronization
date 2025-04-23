package com.travelsync.traveldatasync.model.notification;

import com.travelsync.traveldatasync.model.notification.notification_status.NotificationStatus;
import com.travelsync.traveldatasync.model.notification.notification_type.NotificationType;
import com.travelsync.traveldatasync.model.user.User;
import com.travelsync.traveldatasync.model.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor

public class NotificationService {
    private final UserService userService;

    public Notification createEmailNotification(String emailMessage){
        User user = userService.getAuthenticatedUser();
        Notification notification = new Notification();
        notification.setNotificationStatus(NotificationStatus.SENT);
        notification.setNotificationType(NotificationType.EMAIL);
        notification.setMessage(emailMessage.toString());
        notification.setUser(user);
        return notification;
    }
}
