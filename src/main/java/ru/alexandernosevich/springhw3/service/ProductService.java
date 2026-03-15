package ru.alexandernosevich.springhw3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandernosevich.springhw3.entity.Product;
import ru.alexandernosevich.springhw3.repository.CustomerRepository;
import ru.alexandernosevich.springhw3.repository.OrderRepository;
import ru.alexandernosevich.springhw3.repository.ProductRepository;
import tools.jackson.databind.ObjectMapper;
import ru.alexandernosevich.springhw3.exception.ResourceNotFoundException;
import ru.alexandernosevich.springhw3.exception.InvalidResourceException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final ObjectMapper objectMapper;

    //Создание товара
    public Product createProduct(Product product) {
        if(productRepository.findById(product.getProductId()).isPresent()) {
            throw new InvalidResourceException("Такой товар уже имеется");
        }
        return productRepository.save(product);
    }

    //Обновление товара
    public Product updateProduct(UUID productId, Product updatedProduct) {
        Product existingProduct = getProductById(productId);
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantityInStock(updatedProduct.getQuantityInStock());

        return productRepository.save(existingProduct);
    }

    //Удаление товара
    public void deleteProductById(UUID productId) {
        if(!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Товар с ID " + productId + " не найден");
        }
        productRepository.deleteById(productId);
    }

    //Получить список всех товаров
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //Поиск товара по АйДи
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Товар с АйДи " + productId + " не найден"));
    }

    //Поиск товара по названию
    public Product getProductByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Товар " + name + " не найден"));
    }
}
