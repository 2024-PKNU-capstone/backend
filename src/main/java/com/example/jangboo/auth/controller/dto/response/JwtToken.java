package com.example.jangboo.auth.controller.dto.response;

public record JwtToken(String grantType,String accessToken,String refreshToken) {
}
