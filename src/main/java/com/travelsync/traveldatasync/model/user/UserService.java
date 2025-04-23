package com.travelsync.traveldatasync.model.user;

import com.travelsync.traveldatasync.model.user.mapper.UserMapper;
import com.travelsync.traveldatasync.model.user.request.AddUserRequest;
import com.travelsync.traveldatasync.model.user.response.UserCompanyResponseDto;
import com.travelsync.traveldatasync.model.user.response.UserResponseDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;


    @Override
    public User addUser(AddUserRequest addUserRequest) {
        if (userRepository.existsByEmail(addUserRequest.getEmail())) {
            throw new EntityExistsException("User already exists: " + addUserRequest.getEmail());
        }
        User user = createUser(addUserRequest);
        return userRepository.save(user);
    }

    private User createUser(AddUserRequest addUserRequest) {
      return User.builder()
                .fullName(addUserRequest.getFullName())
                .companyName(addUserRequest.getCompanyName())
                .phoneNumber(addUserRequest.getPhoneNumber())
                .email(addUserRequest.getEmail())
                .password(passwordEncoder.encode(addUserRequest.getPassword()))
                .role(UserRole.TOUR_OPERATOR)
                .build();
    }
    @Override
    public List<UserCompanyResponseDto> findAllCompanyNames() {
        List<User> allUsers = userRepository.findAll();
        List<User> tourOperators = allUsers.stream()
                .filter(user -> user.getRole() == UserRole.TOUR_OPERATOR)
                .collect(Collectors.toUnmodifiableList());

        return userMapper.convertToUserCompanyDtos(tourOperators);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new EntityNotFoundException("User not authenticated");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + email));
    }

    @Override
    public User findByFullName(String fullName) {
        return userRepository.findByFullName(fullName)
                .orElseThrow(() -> new EntityNotFoundException ("User : " +fullName + " doesn't exist"));
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User id :" + userId + " doesn't exist"));
    }
}
