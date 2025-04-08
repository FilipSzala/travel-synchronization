package com.travelsync.traveldatasync.model.trip.mapper;

import com.travelsync.traveldatasync.model.trip.Trip;
import com.travelsync.traveldatasync.model.trip.response.TripResponseDto;
import com.travelsync.traveldatasync.model.trip.trip_location.response.LocationResponseDto;
import com.travelsync.traveldatasync.model.user.User;
import com.travelsync.traveldatasync.model.user.UserService;
import com.travelsync.traveldatasync.model.user.response.UserResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Component

public class TripMapper {
    private final ModelMapper modelMapper;
    private final UserService userService;



    public  TripResponseDto convertToDto(Trip trip) {
        TripResponseDto tripResponseDto = modelMapper.map(trip, TripResponseDto.class);
        UserResponseDto userResponseDto = modelMapper.map(trip.getUser(), UserResponseDto.class);
        LocationResponseDto locationResponseDto = modelMapper.map(trip.getLocation(), LocationResponseDto.class);
        tripResponseDto.setUser(userResponseDto);
        tripResponseDto.setLocation(locationResponseDto);
        return tripResponseDto;
    }
    public  List<TripResponseDto> convertToDtos(List<Trip> trips) {
        return trips.stream().map(trip -> convertToDto(trip)).collect(Collectors.toList());
    }
}
