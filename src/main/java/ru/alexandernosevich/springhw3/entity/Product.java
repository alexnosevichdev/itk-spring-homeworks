package ru.alexandernosevich.springhw3.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

//Товар
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NonNull
    private UUID productId;

    @NotBlank(message = "Название товара должно быть заполнено")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Заполните описание товара")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Введите цену")
    @DecimalMin(value = "0.00", message = "Цена не может быть отрицательной")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotNull(message = "Укажите кол-во товара на складе")
    @Min(value = 0, message = "Товаров не может быть меньше 0")
    private int quantityInStock;

    @ManyToMany
    @JoinColumn(name = "order", nullable = false)
    private Order order;
}
