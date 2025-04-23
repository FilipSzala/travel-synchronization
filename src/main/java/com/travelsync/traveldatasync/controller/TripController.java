package com.travelsync.traveldatasync.controller;

import com.travelsync.traveldatasync.model.trip.Trip;
import com.travelsync.traveldatasync.model.trip.TripService;
import com.travelsync.traveldatasync.model.trip.mapper.TripMapper;
import com.travelsync.traveldatasync.model.trip.request.AddTripRequest;
import com.travelsync.traveldatasync.model.trip.request.UpdateTripRequest;
import com.travelsync.traveldatasync.model.trip.request.wrapper.TripAvailabilityRequestWrapper;
import com.travelsync.traveldatasync.model.trip.response.TripResponseDto;
import com.travelsync.traveldatasync.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/trips")
public class TripController {
    private final TripService tripService;
    private final TripMapper tripMapper;

    @PostMapping("")
    public ResponseEntity<ApiResponse> addTrip (@RequestBody AddTripRequest tripRequest){
        Trip trip = tripService.addTrip(tripRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Trip added: ", trip.getTitle()));
    }

    @PutMapping ("/{tripId}")
    public ResponseEntity<ApiResponse> updateTrip (@PathVariable Long tripId , @RequestBody UpdateTripRequest tripRequest){
        Trip updatedTrip = tripService.updateTrip(tripId, tripRequest);
        return ResponseEntity.ok(new ApiResponse("Trip updated :", updatedTrip.getTitle()));
    }

    @DeleteMapping ("/{tripId}")
    public ResponseEntity deleteTrip (@PathVariable Long tripId){
       tripService.deleteTrip(tripId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping ("")
    public ResponseEntity<ApiResponse> getAllTrips (){
        List<Trip> trips = tripService.findAllTrips();
        List<TripResponseDto> tripDtos = tripMapper.convertToDtos(trips);
        return ResponseEntity.ok(new ApiResponse("Found", tripDtos));
    }

    @PostMapping ("/notifications")
    public ResponseEntity <ApiResponse> sendNotifications (@RequestBody TripAvailabilityRequestWrapper wrapper) {
        tripService.notifyIfCurrentParticipantChanged(wrapper.getFetchedTrips(), wrapper.getTripsRequest(), wrapper.getTourOperatorId());
        return ResponseEntity.ok(new ApiResponse("Successfull"));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> getAllTripsByUserId(@PathVariable Long userId) {
        List<Trip> trips = tripService.findAllTripByUserId(userId);
        List<TripResponseDto> tripDtos = tripMapper.convertToDtos(trips);
        return ResponseEntity.ok(new ApiResponse("Found", tripDtos));
    }

    @GetMapping("upcoming/users/{userId}")
    public ResponseEntity<ApiResponse> getUpcomingTripsByUserId(@PathVariable Long userId) {
        List<Trip> trips = tripService.findUpcomingTripsByUserId(userId);
        List<TripResponseDto> tripDtos = tripMapper.convertToDtos(trips);
        return ResponseEntity.ok(new ApiResponse("Found", tripDtos));
    }
}
