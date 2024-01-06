package com.oop.stockcontrol.newDto;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String accessToken;
    private String tokenType;

    public AuthResponseDto(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
