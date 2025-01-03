package com.project.shopapp.repositories;


import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Check valid user //
    boolean existsByPhoneNumber(String phoneNumber);
    // Check user by phone number //
    Optional<User> findByPhoneNumber(String phoneNumber);
}
