package com.travelsync.traveldatasync.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional <User> findByEmail(String email);
    Optional<User> findByCompanyName(String companyName);

    Optional <User> findByFullName(String fullName);

}