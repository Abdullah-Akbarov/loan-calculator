package com.zero.loancalculator.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "credit")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "interest_rate", nullable = false)
    private double interestRate;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "monthly_payment", nullable = false)
    private double monthlyPayment;
    @OneToOne
    private Passport passport;
}
