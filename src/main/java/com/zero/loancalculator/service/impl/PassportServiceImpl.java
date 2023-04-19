package com.zero.loancalculator.service.impl;

import com.zero.loancalculator.domain.Passport;
import com.zero.loancalculator.domain.User;
import com.zero.loancalculator.dto.PassportDto;
import com.zero.loancalculator.dto.RetrievePassportDto;
import com.zero.loancalculator.model.MessageModel;
import com.zero.loancalculator.model.ResponseModel;
import com.zero.loancalculator.repository.PassportRepository;
import com.zero.loancalculator.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {
    private final PassportRepository passportRepository;

    /**
     * This method creates new passport entity.
     *
     * @param passportDto passport details.
     */
    @Override
    public ResponseModel createPassport(PassportDto passportDto) {
        log.info(">> createPassport: " + passportDto);
        boolean isExist = passportRepository.existsBySeriesAndNumber(passportDto.getSeries(), passportDto.getNumber());
        if (isExist) {
            log.warn("<< createPassport: Record already exist");
            return new ResponseModel(MessageModel.RECORD_AlREADY_EXIST);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Passport passport = passportDto.toEntity();
        passport.setUser(user);
        log.info("<< createPassport: Success");
        passportRepository.save(passport);
        return new ResponseModel(MessageModel.SUCCESS);
    }

    /**
     * This method retrieves passport entity from a database.
     *
     * @param retrievePassportDto passport series and number details.
     */
    @Override
    public ResponseModel retrievePassport(RetrievePassportDto retrievePassportDto) {
        log.info(">> retrievePassport: " + retrievePassportDto);
        Optional<Passport> bySeriesAndNumber = passportRepository.findBySeriesAndNumber(retrievePassportDto.getSeries(), retrievePassportDto.getNumber());
        if (!bySeriesAndNumber.isPresent()) {
            log.warn("<< retrievePassport: Record not found");
            return new ResponseModel(MessageModel.NOT_FOUND);
        }
        Passport passport = bySeriesAndNumber.get();
        passport.setUser(null);
        return new ResponseModel(MessageModel.SUCCESS, passport);
    }
}
