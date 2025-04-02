package com.travelsync.traveldatasync.model.user;

import com.travelsync.traveldatasync.model.user.request.AddUserRequest;
import com.travelsync.traveldatasync.model.user.response.UserResponseDto;

public interface IUserService {
    User addUser (AddUserRequest addUserRequest);

    UserResponseDto convertToDto(User user);
}
