/**
 * This class represents the implementation of passport series and number.
 */

package com.zero.loancalculator.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RetrievePassportDto {
    private String serial;
    private String number;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial.toUpperCase();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
