package com.travelsync.traveldatasync.model.user.mapper;

import com.travelsync.traveldatasync.model.user.User;
import com.travelsync.traveldatasync.model.user.response.UserCompanyResponseDto;
import com.travelsync.traveldatasync.model.user.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserResponseDto convertToUserDto(User user) {
        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
        return userDto;
    }
    private UserCompanyResponseDto convertToUserCompanyDto(User user) {
        UserCompanyResponseDto userDto = modelMapper.map(user, UserCompanyResponseDto.class);
        return userDto;
    }
    public List<UserCompanyResponseDto> convertToUserCompanyDtos(List<User> users) {
        return users.stream().map(user -> convertToUserCompanyDto(user)).collect(Collectors.toList());
    }
}
