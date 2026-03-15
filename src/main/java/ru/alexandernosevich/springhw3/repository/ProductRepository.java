package ru.alexandernosevich.springhw3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandernosevich.springhw3.entity.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);
}
