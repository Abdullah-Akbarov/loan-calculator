package com.zero.loancalculator.repository;

import com.zero.loancalculator.domain.Credit;
import com.zero.loancalculator.domain.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    boolean existsByPassport(Passport passport);

    Optional<Credit> findByPassport(Passport passport);
}
