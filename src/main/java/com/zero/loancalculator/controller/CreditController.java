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
