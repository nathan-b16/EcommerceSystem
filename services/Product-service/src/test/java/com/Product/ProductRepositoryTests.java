package com.Product;

import Product.ProductServiceApplication;
import Product.Model.Product;
import Product.Model.ProductCategory;
import Product.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest(properties = "de.flapdoodle.mongodb.embedded.version=7.0.5")
@ContextConfiguration(classes = ProductServiceApplication.class)
class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    @Test
    void ProductRepository_Saved_Products(){
        //Arrange
        Product product = Product.builder()
                .productId("X159")
                .productName("Apple")
                .price(1.20)
                .category(ProductCategory.FOOD)
                .quantity(50)
                .build();

        ///Act
        Product saved = repository.save(product);

        //Assert
        assertNotNull(saved);
        assertEquals(product.getProductId(), saved.getProductId());
    }
    @Test
    void ProductRepository_Return_MoreThenOneProduct(){
        Product product = Product.builder()
                .productId("X159")
                .productName("Apple")
                .price(1.20)
                .category(ProductCategory.FOOD)
                .quantity(50)
                .build();
        Product product2 = Product.builder()
                .productId("X179")
                .productName("Banana")
                .price(2.20)
                .category(ProductCategory.FOOD)
                .quantity(70)
                .build();
        repository.save(product);
        repository.save(product2);

        List<Product> productList = repository.findAll();

        assertNotNull(productList);
        assertEquals(2, productList.size());

    }
}
