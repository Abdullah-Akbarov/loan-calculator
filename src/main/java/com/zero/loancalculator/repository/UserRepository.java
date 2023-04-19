package com.zero.loancalculator.repository;

import com.zero.loancalculator.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameAndPhoneNumber(String username, String phoneNumber);
}
