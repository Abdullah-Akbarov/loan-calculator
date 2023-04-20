package com.zero.loancalculator.service.impl;

import com.zero.loancalculator.domain.User;
import com.zero.loancalculator.dto.JwtDto;
import com.zero.loancalculator.dto.LoginDto;
import com.zero.loancalculator.dto.UserDto;
import com.zero.loancalculator.model.MessageModel;
import com.zero.loancalculator.model.ResponseModel;
import com.zero.loancalculator.repository.UserRepository;
import com.zero.loancalculator.service.AuthService;
import com.zero.loancalculator.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * This method is used authenticate existing user.
     *
     * @param loginDto User login details.
     * @return jwt token.
     */
    @Override
    public ResponseModel login(LoginDto loginDto) {
        ResponseModel responseModel = validateLoginDetails(loginDto);
        if (responseModel != null) {
            return responseModel;
        }
        log.info(">> login: username=" + loginDto.getUsername());
        Optional<User> username = userRepository.findByUsername(loginDto.getUsername());
        if (username.isEmpty()) {
            log.warn("<< login: Not found");
            return new ResponseModel(MessageModel.NOT_FOUND);
        }
        if (encoder.matches(loginDto.getPassword(), username.get().getPassword())) {
            log.info("<< login: success");
            return new ResponseModel(MessageModel.SUCCESS, new JwtDto("Bearer", jwtTokenUtil.generateToken(username.get())));
        }
        log.warn("<< login: Authentication failed");
        return new ResponseModel(MessageModel.AUTHENTICATION_FAILED);
    }

    /**
     * This method is used to save new User entity in a database.
     *
     * @param userDto The User information to save.
     * @return jwt token.
     */
    @Override
    public ResponseModel register(UserDto userDto) {
        ResponseModel responseModel = validateRegisterDetails(userDto);
        if (responseModel != null) {
            return responseModel;
        }
        log.info(">> register: username=" + userDto.getUsername() + " phoneNumber=" + userDto.getPhoneNumber());
        if (userRepository.existsByUsernameOrPhoneNumber(userDto.getUsername(), userDto.getPhoneNumber())) {
            log.warn("<< register: Record already exist");
            return new ResponseModel(MessageModel.RECORD_AlREADY_EXIST);
        }
        User user = userDto.toEntity();
        encodePassword(user);
        try {
            userRepository.save(user);
            log.info("<< register: Success");
            return new ResponseModel(MessageModel.SUCCESS);
        } catch (Exception e) {
            log.warn("<< saveUser: Couldn't save record exception=" + e.getMessage());
            return new ResponseModel(MessageModel.COULD_NOT_SAVE_RECORD, e.getMessage());
        }
    }

    /**
     * This method encodes User password.
     */
    private void encodePassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }

    private ResponseModel validateRegisterDetails(UserDto userDto) {
        String password = userDto.getPassword();
        String phoneNumber = userDto.getPhoneNumber();
        String username = userDto.getUsername();
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
        return null;
    }

    private ResponseModel validateLoginDetails(LoginDto loginDto) {
        if (loginDto.getUsername().length() < 5) {
            log.warn("<< login: Invalid username");
            return new ResponseModel(MessageModel.USERNAME_TOO_SHORT);
        }
        if (loginDto.getPassword().length() < 8) {
            log.warn("<< login: Invalid password");
            return new ResponseModel(MessageModel.PASSWORD_TOO_SHORT);
        }
        return null;
    }
}
