package ru.alexandernosevich.springoauthhw.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.alexandernosevich.springoauthhw.enums.Role;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //github логин
    @Column(nullable = false)
    private String login;

    //отображаемый юзернейм
    @Column(nullable = false)
    private String name;

    @Email
    private String email;

    private Role role;
}
