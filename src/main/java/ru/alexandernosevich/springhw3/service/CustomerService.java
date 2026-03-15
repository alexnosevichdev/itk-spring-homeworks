package ru.alexandernosevich.springhw3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandernosevich.springhw3.entity.Customer;
import ru.alexandernosevich.springhw3.repository.CustomerRepository;
import tools.jackson.databind.ObjectMapper;
import ru.alexandernosevich.springhw3.exception.ResourceNotFoundException;
import ru.alexandernosevich.springhw3.exception.InvalidResourceException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final ObjectMapper objectMapper;


    //Создание покупателя
    public Customer createCustomer(Customer customer) {
        if(
                customerRepository.findByEmail(customer.getEmail()).isPresent() ||
                customerRepository.findByContactNumber(customer.getContactNumber()).isPresent()
        ) {
            throw new InvalidResourceException("Этот покупатель уже есть в базе");
        }
        return customerRepository.save(customer);
    }

    //Обновление данных покупателя
    public Customer updateCustomer(UUID customerId, Customer updatedCustomer) {
        Customer existingCustomer = getCustomerById(customerId);
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setContactNumber(updatedCustomer.getContactNumber());
        existingCustomer.setLastName(updatedCustomer.getLastName());

        return customerRepository.save(existingCustomer);
    }

    //Удаление покупателя
    public void deleteCustomer(UUID customerId) {
        if(!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Покупатель с ID + " + customerId + " не найден");
        }
        customerRepository.deleteById(customerId);
    }

    //Получить список всех покупателей
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    //Поиск по фамилии
    public List<Customer> getCustomersByLastName(String lastName){
        return customerRepository.findByLastName(lastName);
    }

    //Поиск по почте
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Поиск по почте не дал результатов"));
    }

    //Поиск по АйДи
    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Покупатель с ID " + customerId +
                         " не найден"));
    }

    //Поиск по номеру телефона
    public Customer getCustomerByContactNumber(String contactNumber) {
        return customerRepository.findByContactNumber(contactNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Покупатель с номером телефона " +
                        contactNumber + " не найден"));
    }

    //Поиск по адресу доставки
    public Customer getCustomerByShippingAddress(String shippingAddress) {
        return customerRepository.findByShippingAddress(shippingAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Покупатель с адресом доставки " +
                        shippingAddress + " не найден"));
    }
}
