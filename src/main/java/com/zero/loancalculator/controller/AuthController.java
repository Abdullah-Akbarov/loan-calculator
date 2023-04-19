package com.zero.loancalculator.controller;

import com.zero.loancalculator.dto.LoginDto;
import com.zero.loancalculator.dto.UserDto;
import com.zero.loancalculator.model.MessageModel;
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
        if (loginDto.getUsername().length() < 5) {
            log.warn("<< login: Invalid username");
            return new ResponseModel(MessageModel.USERNAME_TOO_SHORT);
        }
        if (loginDto.getPassword().length() < 8) {
            log.warn("<< login: Invalid password");
            return new ResponseModel(MessageModel.PASSWORD_TOO_SHORT);
        }
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
        String password = userDto.getPassword();
        String phoneNumber = userDto.getPhoneNumber();
        String username = userDto.getUsername();
        log.info(">> saveUser: " + userDto);
        if (username == null || username.length() < 5) {
            log.warn("<< register: Invalid username");
            return new ResponseModel(MessageModel.USERNAME_TOO_SHORT);
        }
        if (password == null || password.length() < 8) {
            log.warn("<< register: Invalid password");
            return new ResponseModel(MessageModel.PASSWORD_TOO_SHORT);
        }
        if (phoneNumber == null || !phoneNumber.matches("^\\+998\\d{9}$")) {
            log.warn("<< register: Invalid phone number");
            return new ResponseModel(MessageModel.INVALID_PHONE_NUMBER);
        }
        try {
            return authService.register(userDto);
        } catch (Exception e) {
            log.warn("<< saveUser: Couldn't save record exception=" + e.getMessage());
            return new ResponseModel(MessageModel.COULD_NOT_SAVE_RECORD, e.getMessage());
        }
    }
}
