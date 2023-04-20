package com.zero.loancalculator.controller;

import com.zero.loancalculator.dto.PassportDto;
import com.zero.loancalculator.dto.RetrievePassportDto;
import com.zero.loancalculator.model.ResponseModel;
import com.zero.loancalculator.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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
        return passportService.createPassport(passportDto);
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
        String series = retrievePassportDto.getSerial();
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
