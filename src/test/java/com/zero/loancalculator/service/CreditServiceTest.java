package com.zero.loancalculator.service;

import com.zero.loancalculator.dto.CreditDto;
import com.zero.loancalculator.model.ResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreditServiceTest {
    @Autowired
    private CreditService creditService;

    @Test
    @WithUserDetails(value = "user45", userDetailsServiceBeanName = "userDetailsService")
    void testGetCredit(){
        CreditDto creditDto = new CreditDto();
        creditDto.setAmount(6300);
        creditDto.setInterestRate(10);
        creditDto.setProfit(1000);
        creditDto.setDescription("Auto Credit");
        creditDto.setMonthsLength(10);
        creditDto.setPassportSerial("AA");
        creditDto.setPassportNumber("4003340");
        ResponseModel credit = creditService.getCredit(creditDto);

        assertEquals(200, credit.status);
    }

    @Test
    @WithUserDetails(value = "user45", userDetailsServiceBeanName = "userDetailsService")
    void testRetrieveCredit(){
        ResponseModel responseModel = creditService.retrieveCredit();
        assertEquals(200, responseModel.status);
    }
}
