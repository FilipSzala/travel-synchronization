package com.travelsync.traveldatasync.model.user;

import com.travelsync.traveldatasync.model.user.request.AddUserRequest;
import com.travelsync.traveldatasync.model.user.response.UserResponseDto;

import java.util.List;

public interface IUserService {
    User addUser (AddUserRequest addUserRequest);
    UserResponseDto convertToDto(User user);
    List<String> findAllCompanyNames();

    User getAuthenticatedUser();

    User findByFullName(String fullName);

    User findById(Long userId);
}
