package com.zero.loancalculator.controller;

import com.zero.loancalculator.dto.CreditDto;
import com.zero.loancalculator.model.ResponseModel;
import com.zero.loancalculator.service.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/credits")
@RequiredArgsConstructor
public class CreditController {
    private final CreditService creditService;

    /**
     * This method handles the POST request for the credit endpoint.
     * It saves new Credit.
     *
     * @param creditDto credit information.
     */
    @PostMapping
    public ResponseModel getCredit(@RequestBody CreditDto creditDto) {
        log.info(">> getCredit: " + creditDto);
        String passportNumber = creditDto.getPassportNumber();
        String passportSeries = creditDto.getPassportSeries();
        double interestRate = creditDto.getInterestRate();
        double profit = creditDto.getProfit();
        double amount = creditDto.getAmount();
        int monthsLength = creditDto.getMonthsLength();
        if (passportSeries == null || passportSeries.length() != 2) {
            log.warn("<< getCredit: Invalid passport series=" + passportSeries);
            return new ResponseModel(503, "Invalid passport series");
        }
        if (passportNumber == null || passportNumber.length() != 7) {
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
        return creditService.getCredit(creditDto);
    }

    /**
     * This method handles the GET request for the credit endpoint.
     * IT retrieves the credit connected to current users passport.
     */
    @GetMapping
    public ResponseModel retrieveCredit() {
        log.info(">> retrieveCredit");
        return creditService.retrieveCredit();
    }
}
