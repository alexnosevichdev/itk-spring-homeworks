package ru.alexnosevichdev.springhomeworkjwt.dto.request;

import ru.alexnosevichdev.springhomeworkjwt.enums.Role;

public record RegisterRequest(String username, String password, Role role) {
}
