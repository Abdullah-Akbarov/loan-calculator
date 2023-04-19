package com.zero.loancalculator.service;

import com.zero.loancalculator.dto.CreditDto;
import com.zero.loancalculator.model.ResponseModel;

public interface CreditService {
    ResponseModel getCredit(CreditDto creditDto);

    ResponseModel retrieveCredit();
}
