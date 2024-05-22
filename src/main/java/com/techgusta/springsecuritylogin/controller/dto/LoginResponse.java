package com.techgusta.springsecuritylogin.controller.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
