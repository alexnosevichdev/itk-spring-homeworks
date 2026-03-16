package ru.alexandernosevich.springhwdatajdbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    private UUID id;

    @NotBlank(message = "Введите название книги")

    private String title;

    @NotBlank(message = "Укажите автора")

    @Pattern(
            regexp = "^[A-ZА-Я][a-zа-я]+\\s[A-ZА-Я][a-zа-я]+\\s[A-ZА-Я][a-zа-я]+$"
    )
    private String author;

    @NotNull(message = "Укажите год издания")
    @Digits(integer = 4, fraction = 0)
    @Max(2026)
    @Positive
    private Integer publicationYear;

}