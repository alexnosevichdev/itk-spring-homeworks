package ru.alexnosevichdev.springhomeworkjwt.dto.response;

import ru.alexnosevichdev.springhomeworkjwt.enums.Role;

public record UserDto(
        Long id,
        String username,
        Role role
) {
}
