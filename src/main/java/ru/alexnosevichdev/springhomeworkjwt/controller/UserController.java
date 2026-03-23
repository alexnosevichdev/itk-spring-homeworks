package ru.alexnosevichdev.springhomeworkjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexnosevichdev.springhomeworkjwt.dto.response.UserDto;
import ru.alexnosevichdev.springhomeworkjwt.service.AuthService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'SUPER_ADMIN')")
    public ResponseEntity<UserDto> getProfile(
            Authentication authentication
    ) {
        String username = authentication.getName();
        return ResponseEntity.ok(authService.getProfile(username));
    }

    @GetMapping("/moderator/content")
    @PreAuthorize("hasAnyRole('MODERATOR', 'SUPER_ADMIN')")
    public ResponseEntity<String> moderatorContent() {
        return ResponseEntity.ok("Вы зашли как модератор");
    }

    @GetMapping("/admin/unlock/{username}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> unlockUser(
            @PathVariable String username
    ) {
        authService.unlockAccount(username);
        return ResponseEntity.ok("Аккаунт " + username + " разлочен");
    }
}
