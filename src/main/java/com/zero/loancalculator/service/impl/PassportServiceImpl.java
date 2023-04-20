package com.zero.loancalculator.service.impl;

import com.zero.loancalculator.domain.Passport;
import com.zero.loancalculator.domain.User;
import com.zero.loancalculator.domain.enums.Gender;
import com.zero.loancalculator.dto.PassportDto;
import com.zero.loancalculator.dto.RetrievePassportDto;
import com.zero.loancalculator.model.MessageModel;
import com.zero.loancalculator.model.ResponseModel;
import com.zero.loancalculator.repository.PassportRepository;
import com.zero.loancalculator.repository.UserRepository;
import com.zero.loancalculator.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {
    private final UserRepository userRepository;
    private final PassportRepository passportRepository;

    /**
     * This method creates new passport entity.
     *
     * @param passportDto passport details.
     */
    @Override
    public ResponseModel createPassport(PassportDto passportDto) {
        ResponseModel responseModel = validatePassportDetails(passportDto);
        if (responseModel != null) {
            return responseModel;
        }
        log.info(">> createPassport: " + passportDto);
        boolean isExist = passportRepository.existsBySerialAndNumber(passportDto.getSerial(), passportDto.getNumber());
        if (isExist) {
            log.warn("<< createPassport: Record already exist");
            return new ResponseModel(MessageModel.RECORD_AlREADY_EXIST);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Passport passport = passportDto.toEntity();
        try {
            Passport save = passportRepository.save(passport);
            user.setPassport(save);
            userRepository.save(user);
            log.info("<< createPassport: Success");
            return new ResponseModel(MessageModel.SUCCESS);
        } catch (Exception e) {
            log.warn("<< createPassport: Couldn't save record exception=" + e.getMessage());
            return new ResponseModel(MessageModel.COULD_NOT_SAVE_RECORD, e.getMessage());
        }
    }

    /**
     * This method retrieves passport entity from a database.
     *
     * @param retrievePassportDto passport series and number details.
     */
    @Override
    public ResponseModel retrievePassport(RetrievePassportDto retrievePassportDto) {
        log.info(">> retrievePassport: " + retrievePassportDto);
        Optional<Passport> bySeriesAndNumber = passportRepository.findBySerialAndNumber(retrievePassportDto.getSerial(), retrievePassportDto.getNumber());
        if (bySeriesAndNumber.isEmpty()) {
            log.warn("<< retrievePassport: Record not found");
            return new ResponseModel(MessageModel.NOT_FOUND);
        }
        Passport passport = bySeriesAndNumber.get();
        return new ResponseModel(MessageModel.SUCCESS, passport);
    }

    /**
     * This method checks if passport details are correct.
     *
     * @param passportDto ResponseModel.
     */
    private ResponseModel validatePassportDetails(PassportDto passportDto) {
        LocalDate birthDate = passportDto.getBirthDate();
        LocalDate expiryDate = passportDto.getExpiryDate();
        LocalDate issueDate = passportDto.getIssueDate();
        String firstName = passportDto.getFirstName();
        String lastName = passportDto.getLastName();
        String fatherName = passportDto.getFatherName();
        Gender gender = passportDto.getGender();
        String serial = passportDto.getSerial();
        String number = passportDto.getNumber();
        if (birthDate == null) {
            return new ResponseModel(503, "birthdate cannot be null");
        }
        Period age = Period.between(birthDate, LocalDate.now());
        if (gender == null || gender != Gender.MALE && gender != Gender.FEMALE) {
            log.warn("<< savePassport: Invalid gender=" + gender);
            return new ResponseModel(503, "Invalid gender");
        }
        if (serial == null || serial.length() != 2 || !serial.matches("^[A-Za-z]+$")) {
            log.warn("<< savePassport: Invalid passport serial=" + serial);
            return new ResponseModel(503, "Invalid passport serial");
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
        return null;
    }
}
