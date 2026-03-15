package ru.alexandernosevich.springhw3.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Покупатель
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID customerId;

    @NotBlank(message = "Заполните имя покупателя")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Заполните фамилию покупателя")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "Введите номер телефона клиента в формате 7")
    @Column(nullable = false)
    @Size(min = 11, max = 11, message = "Номер должен начинаться на 7 и состоять из 11 цифр")
    @Pattern(regexp = "\\d{11}", message = "Номер должен состоять из 11 цифр")
    private String contactNumber;

    @NotNull(message = "Заполните email")
    @Email
    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orderList = new ArrayList<>();

    //Добавить заказ покупателю
    public void addOrder(Order order) {
        orderList.add(order);
        order.setCustomer(this);
    }

    //Удалить заказ у покупателя
    public void deleteOrder(Order order) {
        orderList.remove(order);
        order.setCustomer(null);
    }

}
