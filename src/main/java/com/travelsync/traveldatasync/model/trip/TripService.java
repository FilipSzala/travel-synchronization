package com.travelsync.traveldatasync.model.trip;
import com.travelsync.traveldatasync.model.email.EmailService;
import com.travelsync.traveldatasync.model.notification.NotificationService;
import com.travelsync.traveldatasync.model.reservation_history.ReservationHistoryService;
import com.travelsync.traveldatasync.model.trip.request.AddTripRequest;
import com.travelsync.traveldatasync.model.trip.request.TripAvailabilityRequest;
import com.travelsync.traveldatasync.model.trip.request.UpdateTripRequest;
import com.travelsync.traveldatasync.model.trip.trip_location.TripLocation;
import com.travelsync.traveldatasync.model.trip.trip_location.TripRepository;
import com.travelsync.traveldatasync.model.user.User;
import com.travelsync.traveldatasync.model.user.UserRepository;
import com.travelsync.traveldatasync.model.user.UserRole;
import com.travelsync.traveldatasync.model.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TripService implements ITripService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final TripRepository tripRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final ReservationHistoryService reservationService;

    @Override
    public Trip addTrip(AddTripRequest tripRequest) {
        Trip trip = createTrip(tripRequest);
        assignOrganizerBasedOnUserRole(trip, tripRequest);
        return tripRepository.save(trip);
    }

    private Trip createTrip(AddTripRequest tripRequest) {
        TripLocation location = tripRequest.getLocation();
        Trip trip = Trip.builder()
                .title(tripRequest.getTitle())
                .description(tripRequest.getDescription())
                .startDate(tripRequest.getStartDate())
                .endDate(tripRequest.getEndDate())
                .maxParticipantCount(tripRequest.getMaxParticipantCount())
                .currentParticipantCount(tripRequest.getCurrentParticipantCount())
                .price(tripRequest.getPrice())
                .location(location)
                .tripCategory(tripRequest.getTripCategory()).build();
        location.setTrip(trip);
        return trip;
    }

    private void assignOrganizerBasedOnUserRole(Trip trip, AddTripRequest addTripRequest) {
        User loggedUser = userService.getAuthenticatedUser();
        if (UserRole.TOUR_OPERATOR.equals(loggedUser.getRole())) {
            trip.setUser(loggedUser);
        } else if (UserRole.ADMIN.equals(loggedUser.getRole())) {
            String companyName = addTripRequest.getUserCompanyName();
            trip.setUser(userRepository.findByCompanyName
                            (companyName)
                    .orElseThrow(() ->
                            new EntityNotFoundException("Company name : " + companyName + " not found in database")));
        } else {
            throw new IllegalStateException("Unsupported user role: " + loggedUser.getRole());
        }
    }

    @Override
    public Trip findById(Long tripId) {
        return tripRepository.findById(tripId).orElseThrow(() ->
                new EntityNotFoundException("Trip id :" + tripId + " doesn't exist"));
    }

    @Override
    public Trip updateTrip(Long tripId, UpdateTripRequest updateTrip) {
        if (updateTrip.hasEmptyField()) {
            throw new IllegalArgumentException("Request can't have empty fields");
        }
        User user = userService.findByFullName(updateTrip.getUserFullName());
        Trip existingTrip = findById(tripId);

        existingTrip.setTitle(updateTrip.getTitle());
        existingTrip.setDescription(updateTrip.getDescription());
        existingTrip.setStartDate(updateTrip.getStartDate());
        existingTrip.setEndDate(updateTrip.getEndDate());
        existingTrip.setMaxParticipantCount(updateTrip.getMaxParticipantCount());
        existingTrip.setCurrentParticipantCount(updateTrip.getCurrentParticipantCount());
        existingTrip.setPrice(updateTrip.getPrice());
        existingTrip.setLocation(updateTrip.getLocation());
        existingTrip.setTripCategory(updateTrip.getTripCategory());
        existingTrip.setUser(user);
        tripRepository.save(existingTrip);
        return existingTrip;
    }

    @Override
    public void deleteTrip(Long tripId) {
        Trip trip = findById(tripId);
        tripRepository.delete(trip);
    }

    @Override
    public List<Trip> findAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public List<Trip> findAllTripByUserId(Long userId) {
        return tripRepository.findByUserId(userId);
    }

    @Override
    public List<Trip> findUpcomingTripsByUserId(Long userId) {
        return tripRepository.findByUserIdAndStartDateGreaterThan(userId, LocalDate.now());
    }

    @Transactional
    public void notifyIfCurrentParticipantChanged(List<TripAvailabilityRequest> trips, List<TripAvailabilityRequest> tripAvailabilities, Long tourOperatorId) {
        StringJoiner emailMessage = new StringJoiner("\n\n");
        List <Trip> updatedTrips;
        updatedTrips = updateTripsAndCollectAvailabilityChanges(trips, tripAvailabilities, emailMessage);
        emailService.sendNotificationEmailBasedOnUserRole(updatedTrips, emailMessage, tourOperatorId);
        reservationService.createReservationHistoryWithNotification(emailMessage, updatedTrips);
    }


    private List<Trip> updateTripsAndCollectAvailabilityChanges(List<TripAvailabilityRequest> trips, List<TripAvailabilityRequest> tripAvailabilities, StringJoiner emailMessage) {
        List <Trip> updatedTrips = new ArrayList<>();

        if (trips.size()==tripAvailabilities.size()) {
            IntStream.range(0, trips.size())
                    .forEach(i -> {
                        TripAvailabilityRequest trip = trips.get(i);
                        TripAvailabilityRequest tripAvailability = tripAvailabilities.get(i);
                        int participant = trip.getCurrentParticipantCount();
                        int participantAfterChanged = tripAvailability.getCurrentParticipantCount();
                        if (participant != participantAfterChanged) {
                            Trip fetchedTrip = findById(trip.getTripId());
                            String message = emailService.generateEmailMessage(fetchedTrip, participant, participantAfterChanged);
                            fetchedTrip.setCurrentParticipantCount(participantAfterChanged);
                            tripRepository.save(fetchedTrip);
                            updatedTrips.add(fetchedTrip);
                            emailMessage.add(message);
                        }
                    });
        }
        else {
            throw new IllegalArgumentException("The number of trips and availability entries must be the same.");
        }
        return updatedTrips;
    }




}
