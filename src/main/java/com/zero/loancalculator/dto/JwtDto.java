/**
 * This class represents the implementation of JwtDto.
 */

package com.zero.loancalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {
    private String tokenType;
    private String token;
}
