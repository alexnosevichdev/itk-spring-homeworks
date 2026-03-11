package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Views;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @JsonView(Views.UserSummary.class)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @JsonView(Views.UserDetails.class)
    public User getUserById(@PathVariable UUID id) {
        return userService.getByUserId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.UserSummary.class)
    public User createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    @JsonView(Views.UserSummary.class)
    public User updateUser(@PathVariable UUID id, @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

}
