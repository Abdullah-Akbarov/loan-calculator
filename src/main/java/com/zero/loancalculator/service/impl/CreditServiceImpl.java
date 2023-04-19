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
        log.info(">> getCredit: " + creditDto);
        Passport passport = getCurrentUser().getPassport();
        if (passport == null || !(passport.getSeries().equals(creditDto.getPassportSeries())
                && passport.getNumber().equals(creditDto.getPassportNumber()))) {
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
        Credit save = creditRepository.save(credit);
        log.info("<< getCredit: Success");
        return new ResponseModel(MessageModel.SUCCESS, save);
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
}
