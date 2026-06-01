package com.scholaris.dto;

public record LoginResponse(
        String token,
        String tokenType,
        long expiresInSeconds,
        String username,
        String fullName,
        String role
) {
}
