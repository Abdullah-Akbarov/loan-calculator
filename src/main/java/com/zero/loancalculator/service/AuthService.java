package com.zero.loancalculator.service;


import com.zero.loancalculator.dto.LoginDto;
import com.zero.loancalculator.dto.UserDto;
import com.zero.loancalculator.model.ResponseModel;

public interface AuthService {
    ResponseModel login(LoginDto loginDto);

    ResponseModel register(UserDto userDto);
}
