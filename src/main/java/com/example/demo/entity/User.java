package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonView(Views.UserSummary.class)
    private UUID id;

    @Email
    @NotBlank(message = "Это обязательно поле")
    @Column(nullable = false, unique = true)
    @JsonView(Views.UserSummary.class)
    private String email;

    @NotBlank(message = "Это обязательное поле")
    @Column(nullable = false)
    @JsonView(Views.UserSummary.class)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.UserDetails.class)
    private List<Order> orderList = new ArrayList<>();

    //Добавить заказ
    public void addOrder(Order order) {
        orderList.add(order);
        order.setUser(this);
    }

    //Удалить заказ
    public void deleteOrder(Order order) {
        orderList.remove(order);
        order.setUser(null);
    }
}
