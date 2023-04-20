package com.zero.loancalculator.service.impl;

import com.zero.loancalculator.domain.Credit;
import com.zero.loancalculator.domain.Passport;
import com.zero.loancalculator.domain.User;
import com.zero.loancalculator.dto.CreditDto;
import com.zero.loancalculator.model.MessageModel;
import com.zero.loancalculator.model.ResponseModel;
import com.zero.loancalculator.repository.CreditRepository;
import com.zero.loancalculator.repository.PassportRepository;
import com.zero.loancalculator.service.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final PassportRepository passportRepository;

    /**
     * This method is used calculate credit details and save.
     *
     * @param creditDto credit details.
     */
    @Override
    public ResponseModel getCredit(CreditDto creditDto) {
        ResponseModel responseModel = validateCreditDetails(creditDto);
        if (responseModel != null) {
            return responseModel;
        }
        log.info(">> getCredit: " + creditDto);
        Optional<Passport> bySerialAndNumber = passportRepository.findBySerialAndNumber(creditDto.getPassportSerial(), creditDto.getPassportNumber());
        Passport passport = getCurrentUser().getPassport();
        if (passport == null || !(passport.equals(bySerialAndNumber.get()))) {
            log.warn("<< getCredit: Unauthorized");
            return new ResponseModel(MessageModel.UNAUTHORIZED);
        }
        if (creditRepository.existsByPassport(passport)) {
            log.warn("<< getCredit: Credit has been taken by this passport");
            return new ResponseModel(MessageModel.CREDIT_TAKEN_BEFORE);
        }
        double profit = creditDto.getProfit();
        int monthsLength = creditDto.getMonthsLength();
        double pureProfit = profit * 0.7;
        double totalProfit = pureProfit * monthsLength;
        double amount = creditDto.getAmount();
        double interestRate = creditDto.getInterestRate();
        double creditAmount = amount + (amount * (interestRate / 100));
        String description = creditDto.getDescription();
        if (totalProfit < creditAmount) {
            log.warn("<< getCredit: insufficient profit");
            return new ResponseModel(407, "profit is not enough to cover requested loan with interest, " +
                    "maximum loan limit is " + totalProfit * ((100 - interestRate) / 100));
        }
        Credit credit = new Credit();
        LocalDate localDate = LocalDate.now();
        credit.setPassport(passport);
        credit.setAmount(creditAmount);
        credit.setMonthlyPayment(creditAmount / creditDto.getMonthsLength());
        credit.setInterestRate(interestRate);
        credit.setStartDate(localDate);
        credit.setEndDate(localDate.plusMonths(monthsLength));
        credit.setDescription(description);
        try {
            Credit save = creditRepository.save(credit);
            log.info("<< getCredit: Success");
            return new ResponseModel(MessageModel.SUCCESS, save);
        } catch (Exception e) {
            log.warn("<< getCredit: Couldn't save record exception=" + e.getMessage());
            return new ResponseModel(MessageModel.COULD_NOT_SAVE_RECORD);
        }
    }

    /**
     * This method is used retrieve the taken credit entity.
     */
    @Override
    public ResponseModel retrieveCredit() {
        log.info(">> retrieveCredit");
        User user = getCurrentUser();
        Optional<Credit> byPassport = creditRepository.findByPassport(user.getPassport());
        if (byPassport.isPresent()) {
            log.info("<< retrieveCredit: Success");
            return new ResponseModel(MessageModel.SUCCESS, byPassport.get());
        }
        log.warn("<< retrieveCredit: Not found");
        return new ResponseModel(MessageModel.NOT_FOUND);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }

    private ResponseModel validateCreditDetails(CreditDto creditDto) {
        String passportNumber = creditDto.getPassportNumber();
        String passportSeries = creditDto.getPassportSerial();
        double interestRate = creditDto.getInterestRate();
        double profit = creditDto.getProfit();
        double amount = creditDto.getAmount();
        int monthsLength = creditDto.getMonthsLength();
        if (passportSeries == null || passportSeries.length() != 2 || !passportSeries.matches("^[A-Za-z]+$")) {
            log.warn("<< getCredit: Invalid passport series=" + passportSeries);
            return new ResponseModel(503, "Invalid passport series");
        }
        if (passportNumber == null || passportNumber.length() != 7 || !passportNumber.matches("\\d+")) {
            log.warn("<< getCredit: Invalid passport number=" + passportNumber);
            return new ResponseModel(503, "Invalid passport number");
        }
        if (interestRate < 0 || interestRate > 100) {
            log.warn("<< getCredit: Invalid interest rate=" + interestRate);
            return new ResponseModel(503, "Invalid interest rate");
        }
        if (profit < 0) {
            log.warn("<< getCredit: Invalid profit=" + profit);
            return new ResponseModel(503, "Invalid profit");
        }
        if (amount < 0) {
            log.warn("<< getCredit: Invalid amount=" + amount);
            return new ResponseModel(503, "Invalid amount");
        }
        if (monthsLength < 0) {
            log.warn("<< getCredit: Invalid months length=" + monthsLength);
            return new ResponseModel(503, "Invalid months length");
        }
        return null;
    }
}
