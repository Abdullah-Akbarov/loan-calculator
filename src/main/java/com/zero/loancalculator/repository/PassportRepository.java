package com.zero.loancalculator.repository;

import com.zero.loancalculator.domain.Passport;
import com.zero.loancalculator.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PassportRepository extends JpaRepository<Passport, Long> {
    Optional<Passport> findBySeriesAndNumber(String series, String number);

    boolean existsBySeriesAndNumber(String series, String number);

    Optional<Passport> findByUser(User user);
}
