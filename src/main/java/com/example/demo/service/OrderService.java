package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(UUID userId, Order order) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        order.setUser(user);
        user.addOrder(order);
        if(order.getStatus()==null) {
            order.setStatus(OrderStatus.NEW);
        }
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    public List<Order> getOrderByUser (UUID userId){
        if(!userRepository.existsById(userId)){
            throw new RuntimeException("Пользователь не найден");
        }
        return orderRepository.findByUserId(userId);
    }

    public Order getByOrderId(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ с id " + orderId + " не найден"));
    }

    @Transactional
    public Order updateOrderStatus(UUID orderId, OrderStatus newStatus){
        Order order = getByOrderId(orderId);
        order.setStatus(newStatus);
        return order;
    }

    @Transactional
    public Order updateOrderAmountOfProduct(UUID orderId, BigDecimal newAmount) {
        Order order = getByOrderId(orderId);
        order.setAmount(newAmount);
        return order;
    }

    @Transactional
    public void deleteOrder(UUID orderId){
        if(!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Заказ не найден");
        }
        orderRepository.deleteById(orderId);
    }
}
