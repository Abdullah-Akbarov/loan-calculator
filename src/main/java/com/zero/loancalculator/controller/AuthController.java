package com.zero.loancalculator.controller;

import com.zero.loancalculator.dto.LoginDto;
import com.zero.loancalculator.dto.UserDto;
import com.zero.loancalculator.model.ResponseModel;
import com.zero.loancalculator.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * This method handles GET requests to the /auth/login endpoint.
     * It checks if a user exists and authorized to log in this account.
     *
     * @param loginDto User login information.
     * @return jwt token.
     */
    @GetMapping("/login")
    public ResponseModel login(@RequestBody LoginDto loginDto) {
        log.info(">> login: username=" + loginDto.getUsername());
        return authService.login(loginDto);
    }

    /**
     * This method handles POST requests to the /auth/register endpoint.
     * It creates a new User with the provided information.
     *
     * @param userDto The user information.
     * @return jwt token.
     */
    @PostMapping("/register")
    ResponseModel saveUser(@RequestBody UserDto userDto) {
        log.info(">> register: " + userDto);
        return authService.register(userDto);
    }
}
