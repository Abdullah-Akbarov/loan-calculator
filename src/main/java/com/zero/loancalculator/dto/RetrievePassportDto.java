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
    private String series;
    private String number;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series.toUpperCase();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
