package com.zero.loancalculator.service;

import com.zero.loancalculator.domain.enums.Gender;
import com.zero.loancalculator.dto.PassportDto;
import com.zero.loancalculator.dto.RetrievePassportDto;
import com.zero.loancalculator.model.ResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PassportServiceTest {
    @Autowired
    private PassportService passportService;

    @Test
    @WithUserDetails(value = "user45", userDetailsServiceBeanName = "userDetailsService")
    void testSavePassport(){
        PassportDto passportDto = new PassportDto();
        passportDto.setGender(Gender.MALE);
        passportDto.setNumber("4003340");
        passportDto.setSerial("AA");
        passportDto.setBirthDate(LocalDate.parse("1989-11-30"));
        passportDto.setExpiryDate(LocalDate.parse("2030-10-19"));
        passportDto.setIssueDate(LocalDate.parse("2020-10-20"));
        passportDto.setFatherName( "Falonchi o'g'li");
        passportDto.setFirstName("Falonchi");
        passportDto.setLastName("Falonchiyev");

        ResponseModel passport = passportService.createPassport(passportDto);
        assertEquals(200, passport.status);
    }

    @Test
    @WithUserDetails(value = "user45", userDetailsServiceBeanName = "userDetailsService")
    void testRetrievePassport(){
        RetrievePassportDto retrievePassportDto = new RetrievePassportDto();
        retrievePassportDto.setNumber("4003340");
        retrievePassportDto.setSerial("AA");

        ResponseModel responseModel = passportService.retrievePassport(retrievePassportDto);
        assertEquals(200, responseModel.status);
    }
}
