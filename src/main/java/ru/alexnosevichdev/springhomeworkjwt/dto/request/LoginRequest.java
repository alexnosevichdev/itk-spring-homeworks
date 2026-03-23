package ru.alexnosevichdev.springhomeworkjwt.dto.request;

public record LoginRequest(
        String username,
        String password
) {

}
