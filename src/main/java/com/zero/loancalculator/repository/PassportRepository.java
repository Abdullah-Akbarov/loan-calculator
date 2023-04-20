package com.zero.loancalculator.repository;

import com.zero.loancalculator.domain.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PassportRepository extends JpaRepository<Passport, Long> {
    Optional<Passport> findBySerialAndNumber(String series, String number);

    boolean existsBySerialAndNumber(String series, String number);
}
