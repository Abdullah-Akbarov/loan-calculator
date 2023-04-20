/**
 * This class creates entity of passport in database.
 */

package com.zero.loancalculator.domain;

import com.zero.loancalculator.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "passport")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "serial", nullable = false)
    private String serial;
    @Column(name = "number", nullable = false)
    private String number;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "father_name", nullable = false)
    private String fatherName;
}
