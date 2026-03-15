package ru.alexandernosevich.springhw3.repository;

import ru.alexandernosevich.springhw3.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
    List<Customer> findByLastName(String lastName);
    Optional<Customer> findByContactNumber(String contactNumber);
    Optional<Customer> findByShippingAddress(String shippingAddress);
}
