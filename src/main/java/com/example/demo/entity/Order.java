package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonView(Views.UserDetails.class)
    private UUID orderId;

    @Column(nullable = false)
    @NotBlank(message = "Необходимо название товара")
    @JsonView(Views.UserDetails.class)
    private String productName;

    @NotNull(message = "Сумма не может быть 0")
    @Positive(message = "Сумма не может быть меньше 0")
    @Column(nullable = false, precision = 10, scale = 2)
    @JsonView(Views.UserDetails.class)
    private BigDecimal amount;

    @NotNull(message = "Статус обязателен")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonView(Views.UserDetails.class)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
