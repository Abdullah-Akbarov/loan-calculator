/**
 * This class represents user details.
 */

package com.zero.loancalculator.dto;

import com.zero.loancalculator.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String phoneNumber;

    public User toEntity() {
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
