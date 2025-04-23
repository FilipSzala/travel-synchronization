package com.travelsync.traveldatasync.model.email;
import com.travelsync.traveldatasync.model.trip.Trip;
import com.travelsync.traveldatasync.model.user.User;
import com.travelsync.traveldatasync.model.user.UserRole;
import com.travelsync.traveldatasync.model.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.StringJoiner;

@Service
@AllArgsConstructor

public class EmailService {

        @Autowired
        private JavaMailSender mailSender;
        private UserService userService;
        private final String subjectEmail = "Aktualizacja liczby wolnych miejsc";

        private void sendEmail (String toEmail,String subject,String body){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("biuro@samrazwczas.pl");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        }

    public void sendNotificationEmailBasedOnUserRole(List<Trip> trips, StringJoiner emailMessage, Long tourOperatorId) {
            User loggedUser = userService.getAuthenticatedUser();
            String email;

        if (UserRole.ADMIN.equals(loggedUser.getRole())){
            User tourOperator = userService.findById(tourOperatorId);
            email = tourOperator.getEmail();
        }
        else if (UserRole.TOUR_OPERATOR.equals(loggedUser.getRole())){
            email = "biuro@samrazwczas.pl";
        }
        else {
            throw new IllegalStateException("Unsupported user role: " + loggedUser.getRole());
        }
        sendEmail(email, subjectEmail, emailMessage.toString());
    }

    public String generateEmailMessage(Trip trip, int participant, int participantAfterChanged) {
        String message = "Liczba wolnych miejsc w wycieczce \"" + trip.getTitle() + "\" została zmieniona. "
                + "Poprzednia liczba wolnych miejsc: " + participant + ", aktualna: " + participantAfterChanged + ".";
        return message;
    }
}

