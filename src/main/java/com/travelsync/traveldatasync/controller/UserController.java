package com.travelsync.traveldatasync.controller;

import com.travelsync.traveldatasync.model.user.User;
import com.travelsync.traveldatasync.model.user.UserService;
import com.travelsync.traveldatasync.model.user.request.AddUserRequest;
import com.travelsync.traveldatasync.model.user.response.UserResponseDto;
import com.travelsync.traveldatasync.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> addUser (@RequestBody AddUserRequest addUserRequest){
        User user = userService.addUser(addUserRequest);
        UserResponseDto userDto = userService.convertToDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User added: ", userDto));
    }

}
