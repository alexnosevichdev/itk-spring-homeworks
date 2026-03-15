package ru.alexandernosevich.springhw3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alexandernosevich.springhw3.service.CustomerService;
import ru.alexandernosevich.springhw3.service.OrderService;
import ru.alexandernosevich.springhw3.service.ProductService;
import ru.alexandernosevich.springhw3.entity.Customer;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final ProductService productService;

    private final ObjectMapper objectMapper;

    //Запрос на добавление нового покупателя
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    //Запрос на добавление нового покупателя через ObjectMapper
    @PostMapping("/create/manual")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomerByObjectMapper(@RequestBody String json) throws IOException {
        return objectMapper.readValue(json, Customer.class);
    }


    //Запрос на удаление пользователя
    @DeleteMapping("customer/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
    }

    //Запрос на обновление данных покупателя
    @PutMapping("/customer/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public Customer updateCustomer(@PathVariable UUID id, @Valid @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    //Запрос на поиск покупателя по АйДи
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable UUID id) {
        return customerService.getCustomerById(id);
    }

    //Запрос на поиск покупателя через АйДи с ObjectMapper
    @GetMapping("/{id}/manual")
    public String getCustomerByIdByObjectMapper(@PathVariable UUID id) {
        Customer customer = customerService.getCustomerById(id);
        return objectMapper.writeValueAsString(customer);
    }
}
