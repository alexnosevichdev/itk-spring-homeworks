package ru.alexandernosevich.springhw3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alexandernosevich.springhw3.entity.Product;
import ru.alexandernosevich.springhw3.service.ProductService;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ObjectMapper objectMapper;

    //Запрос на выдачу всех товаров
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //Запрос на поиск товара по ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable UUID productId){
        return productService.getProductById(productId);
    }

    //Запрос на поиск товара по ID через ObjectMapper
    @GetMapping("/{id}/manual")
    public String getProductByIdByObjectMapper(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        return objectMapper.writeValueAsString(product);
    }

    //Запрос на создание товара
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    //Запрос на создание товара через ручную реализацию ObjectMapper
    @PostMapping("/create/manual")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProductByObjectMapper(@RequestBody String json) throws IOException {
        return objectMapper.readValue(json, Product.class);
    }

    //Запрос на обновление данных товара
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable UUID id, @Valid @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    //Запрос на удаление товара
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProductById(id);
    }
}
