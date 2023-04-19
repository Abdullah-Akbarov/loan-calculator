package com.zero.loancalculator.service;

import com.zero.loancalculator.dto.PassportDto;
import com.zero.loancalculator.dto.RetrievePassportDto;
import com.zero.loancalculator.model.ResponseModel;

public interface PassportService {
    ResponseModel createPassport(PassportDto passportDto);

    ResponseModel retrievePassport(RetrievePassportDto retrievePassportDto);
}
