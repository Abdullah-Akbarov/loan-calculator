/**
 * This class represents the implementation of passport information.
 */

package com.zero.loancalculator.dto;

import com.zero.loancalculator.domain.Passport;
import com.zero.loancalculator.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PassportDto {
    private String series;
    private String number;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String firstName;
    private String lastName;
    private String fatherName;

    public Passport toEntity() {
        Passport passport = new Passport();
        passport.setSeries(series);
        passport.setNumber(number);
        passport.setGender(gender);
        passport.setBirthDate(birthDate);
        passport.setIssueDate(issueDate);
        passport.setExpiryDate(expiryDate);
        passport.setFirstName(firstName);
        passport.setLastName(lastName);
        passport.setFatherName(fatherName);
        return passport;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series.toUpperCase();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.toUpperCase();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.toUpperCase();
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName.toUpperCase();
    }
}
