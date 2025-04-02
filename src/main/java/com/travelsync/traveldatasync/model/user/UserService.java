package com.travelsync.traveldatasync.model.user;

import com.travelsync.traveldatasync.model.user.request.AddUserRequest;
import com.travelsync.traveldatasync.model.user.response.UserResponseDto;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


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
                .role("USER")
                .build();
    }
    @Override
    public UserResponseDto convertToDto(User user) {
        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
        return userDto;
    }


}
