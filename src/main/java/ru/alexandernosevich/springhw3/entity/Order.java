package ru.alexandernosevich.springhw3.entity;

import jakarta.validation.constraints.*;
import ru.alexandernosevich.springhw3.entity.OrderStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

//Заказ
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @NonNull
    @Column(nullable = false)
    private LocalDate orderDate;

    @NotBlank(message = "Заполните адрес доставки")
    @Column(nullable = false)
    private String shippingAddress;

    @NotNull(message = "Укажите статус заказа")
    @Column(nullable = false)
    private OrderStatus orderStatus;

//    @NotNull(message = "Заполните данные покупателя")
//    @Column(nullable = false)
//    private Customer customer;

    @NotNull(message = "Введите информацию о товарах")
    @Column(nullable = false)
    private List<Product> products;

    @NotNull(message = "Укажите итоговую стоимость заказа")
    @DecimalMin(value = "0.00")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal totalPrice;

    @ManyToMany
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
