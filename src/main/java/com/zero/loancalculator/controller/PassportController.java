package com.zero.loancalculator.controller;

import com.zero.loancalculator.domain.enums.Gender;
import com.zero.loancalculator.dto.PassportDto;
import com.zero.loancalculator.dto.RetrievePassportDto;
import com.zero.loancalculator.model.MessageModel;
import com.zero.loancalculator.model.ResponseModel;
import com.zero.loancalculator.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;

@Log4j2
@RestController
@RequestMapping("/passport")
@RequiredArgsConstructor
public class PassportController {
    private final PassportService passportService;

    /**
     * This method handles the POST request for the passport/save endpoint.
     * It saves new passport.
     *
     * @param passportDto Passport information.
     */
    @PostMapping("/save")
    public ResponseModel savePassport(@RequestBody PassportDto passportDto) {
        log.info(">> savePassport: " + passportDto);
        LocalDate birthDate = passportDto.getBirthDate();
        LocalDate expiryDate = passportDto.getExpiryDate();
        LocalDate issueDate = passportDto.getIssueDate();
        String firstName = passportDto.getFirstName();
        String lastName = passportDto.getLastName();
        String fatherName = passportDto.getFatherName();
        Gender gender = passportDto.getGender();
        String series = passportDto.getSeries();
        String number = passportDto.getNumber();
        if (birthDate == null) {
            return new ResponseModel(503, "birthdate cannot be null");
        }
        Period age = Period.between(birthDate, LocalDate.now());
        if (gender == null || gender != Gender.MALE && gender != Gender.FEMALE) {
            log.warn("<< savePassport: Invalid gender=" + gender);
            return new ResponseModel(503, "Invalid gender");
        }
        if (series == null || series.length() != 2) {
            log.warn("<< savePassport: Invalid passport series=" + series);
            return new ResponseModel(503, "Invalid passport series");
        }
        if (number == null || number.length() != 7 || !number.matches("\\d+")) {
            log.warn("<< savePassport: Invalid passport number=" + number);
            return new ResponseModel(503, "Invalid passport number");
        }
        if (firstName == null || lastName == null || fatherName == null) {
            log.warn("<< savePassport: First name or last name or father name is null");
            return new ResponseModel(503, "First name or last name or father name is null");
        }
        if (age.getYears() < 18) {
            log.warn("<< savePassport: Age limit is invalid");
            return new ResponseModel(503, "Age must be older than 18 years");
        }
        if (expiryDate == null || issueDate == null || !issueDate.isBefore(expiryDate) && !issueDate.isBefore(LocalDate.now()) && !expiryDate.isAfter(LocalDate.now())) {
            log.warn("<< savePassport: Issue or expiry date is invalid");
            return new ResponseModel(503, "Issue or expiry date is invalid");
        }
        try {
            return passportService.createPassport(passportDto);
        } catch (Exception e) {
            log.warn("<< saveUser: Couldn't save record exception=" + e.getMessage());
            return new ResponseModel(MessageModel.COULD_NOT_SAVE_RECORD, e.getMessage());
        }

    }

    /**
     * This method handles the GET request for the passport/retrieve endpoint.
     * It retrieves passport information.
     *
     * @param retrievePassportDto Passport information.
     */
    @GetMapping("/retrieve")
    public ResponseModel getPassport(@RequestBody RetrievePassportDto retrievePassportDto) {
        log.info(">> getPassport: " + retrievePassportDto);
        String number = retrievePassportDto.getNumber();
        String series = retrievePassportDto.getSeries();
        if (series == null || series.length() != 2) {
            log.warn("<< getPassport: Invalid passport series=" + series);
            return new ResponseModel(503, "Invalid passport series");
        }
        if (number == null || number.length() != 7) {
            log.warn("<< getPassport: Invalid passport number=" + number);
            return new ResponseModel(503, "Invalid passport number");
        }
        return passportService.retrievePassport(retrievePassportDto);
    }
}
