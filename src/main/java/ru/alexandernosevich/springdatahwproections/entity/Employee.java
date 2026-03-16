package ru.alexandernosevich.springdatahwproections.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Заполните имя")
    @Column(nullable = false)
    @Pattern(regexp = "^[A-ZА-Я][a-zа-я]+$")
    private String firstName;

    @NotBlank(message = "Заполните фамилию")
    @Column(nullable = false)
    @Pattern(regexp = "^[A-ZА-Я][a-zа-я]+$")
    private String lastName;

    @NotBlank(message = "Укажите должность")
    @Column(nullable = false)
    //Не пойму, как гибко настроить @Pattern, если в должности может быть
    //несколько слов, а может быть и одно
    //@Pattern
    private String position;

    @NotNull(message = "Укажите зарплату")
    @Column(nullable = false)
    @Positive
    private Integer salary;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

}
