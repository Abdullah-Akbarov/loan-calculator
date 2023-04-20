package com.zero.loancalculator.service;

import com.zero.loancalculator.dto.LoginDto;
import com.zero.loancalculator.dto.UserDto;
import com.zero.loancalculator.model.ResponseModel;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    void testRegister(){
        // I have tested existed user for registration if user exists test fails
        UserDto userDto = new UserDto();
        userDto.setPassword("password");
        userDto.setUsername("user45");
        userDto.setPhoneNumber("+998900001122");
        ResponseModel register = authService.register(userDto);
        System.out.println(register.message);
        assertEquals(200, register.status);
    }
    @Test
    @Transactional
    void testLogin(){
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("password");
        loginDto.setUsername("user45");
        ResponseModel login = authService.login(loginDto);
        System.out.println(login.message);
        assertEquals(200, login.status);
    }
}
