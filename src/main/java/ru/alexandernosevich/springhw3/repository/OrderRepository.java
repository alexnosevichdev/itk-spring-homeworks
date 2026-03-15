package ru.alexandernosevich.springhw3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandernosevich.springhw3.entity.Order;
import ru.alexandernosevich.springhw3.entity.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByOrderDate(LocalDate orderDate);
    Optional<Order> findByShippingAddress(String shippingAddress);
    List<Order> findByStatus(OrderStatus orderStatus);
}
