package ru.alexnosevichdev.springhomeworkjwt.dto.response;

public record AuthResponse(
        String token,
        String refreshToken
) {
}
