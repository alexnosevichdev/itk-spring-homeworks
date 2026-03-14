package ru.alexandernosevich.springhw2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    @NotBlank(message = "Заполните название книги")
    @Column(nullable = false)
    private String bookName;

    @NotBlank(message = "введите жанр")
    @Column(nullable = false)
    private String bookCategory;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookId;

    @NotNull(message = "Год издания обязателен")
    @Column(nullable = false)
    private Integer bookYearOfRelease;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
