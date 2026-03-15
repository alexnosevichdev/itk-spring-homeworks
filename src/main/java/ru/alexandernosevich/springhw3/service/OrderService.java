package ru.alexandernosevich.springhw3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandernosevich.springhw3.entity.Order;
import ru.alexandernosevich.springhw3.entity.OrderStatus;
import ru.alexandernosevich.springhw3.repository.CustomerRepository;
import ru.alexandernosevich.springhw3.repository.OrderRepository;
import ru.alexandernosevich.springhw3.entity.Customer;
import tools.jackson.databind.ObjectMapper;
import ru.alexandernosevich.springhw3.exception.ResourceNotFoundException;
import ru.alexandernosevich.springhw3.exception.InvalidResourceException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    private final ObjectMapper objectMapper;



    //Получить список всех заказов
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    //Создание заказа
    @Transactional
    public Order createOrder(UUID customerId, Order order) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с ID " +
                        customerId + " не найден"));
        order.setCustomer(customer);
        customer.addOrder(order);

        if(order.getOrderStatus() == null) {
            order.setOrderStatus(OrderStatus.CREATED);
        }
        return orderRepository.save(order);
    }

    //Обновление заказа
    public Order updateOrder (Order order, UUID orderId, Order updatedOrder, OrderStatus orderStatus) {
        Order existingOrder = getOrderById(orderId);
        existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
        existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
        existingOrder.setTotalPrice(updatedOrder.getTotalPrice());

        return orderRepository.save(existingOrder);
    }

    //Удаление заказа
    public void deleteOrder(UUID orderId) {
        if(!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Заказ " + orderId + " не найден");
        }
        orderRepository.deleteById(orderId);
    }

    //Поиск заказа по АйДи
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Заказ " + orderId + " не найден"));
    }

    //Получить список заказов по статусу
    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.findByStatus(orderStatus);
    }


}
