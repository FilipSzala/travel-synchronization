package com.travelsync.traveldatasync.controller;

import com.travelsync.traveldatasync.model.user.User;
import com.travelsync.traveldatasync.model.user.UserService;
import com.travelsync.traveldatasync.model.user.mapper.UserMapper;
import com.travelsync.traveldatasync.model.user.request.AddUserRequest;
import com.travelsync.traveldatasync.model.user.response.UserCompanyResponseDto;
import com.travelsync.traveldatasync.model.user.response.UserResponseDto;
import com.travelsync.traveldatasync.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("")
    public ResponseEntity<ApiResponse> addUser (@RequestBody AddUserRequest addUserRequest){
        User user = userService.addUser(addUserRequest);
        UserResponseDto userDto = userMapper.convertToUserDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User added: ", userDto));
    }

    @GetMapping("/tour-operators/companies")
    public ResponseEntity<ApiResponse> getAllTourOperatorCompanies() {
        List<UserCompanyResponseDto> companySummaries = userService.findAllCompanyNames();
        return ResponseEntity.ok(new ApiResponse("Found", companySummaries));
    }
}
