package ru.alexandernosevich.springhw3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alexandernosevich.springhw3.entity.Order;
import ru.alexandernosevich.springhw3.service.OrderService;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    private final ObjectMapper objectMapper;

    //Запрос на оформление нового заказа
    @PostMapping("/create/{customerId}")
    public Order createOrder(@PathVariable UUID customerId, @RequestBody Order order) {
        return orderService.createOrder(customerId, order);
    }

    //Запрос на оформление нового заказа с ObjectMapper
    @PostMapping("/create/manual")
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrderByObjectMapper(@RequestBody String json) throws IOException {
        return objectMapper.readValue(json, Order.class);
    }

    //Запрос на получение информации по заказу
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable UUID id) {
        return orderService.getOrderById(id);
    }

    //Запрос на получение заказа через ObjectMapper
    @GetMapping("/{id}/manual")
    public String getOrderByIdByObjectMapper(@PathVariable UUID id) {
        Order order = orderService.getOrderById(id);
        return objectMapper.writeValueAsString(order);
    }
}
