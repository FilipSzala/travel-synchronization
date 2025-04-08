package com.travelsync.traveldatasync.model.user.user_details;

import com.travelsync.traveldatasync.model.user.User;
import com.travelsync.traveldatasync.model.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserDetailsExtService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = (userRepository.findByEmail(email))
                .orElseThrow(()->new EntityNotFoundException("User not found!"));
        return UserDetailsExt.buildUserDetailsExt(user);
    }
}
