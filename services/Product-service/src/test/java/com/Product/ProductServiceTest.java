package com.Product;

import Product.Model.Product;
import Product.Model.ProductCategory;
import Product.Model.ProductRequest;
import Product.Model.ProductResponse;
import Product.Repository.ProductRepository;
import Product.Service.ProductMapper;
import Product.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;
    @Mock
    private ProductMapper mapper;
    @InjectMocks
    private ProductService service;

    @Test
    void createProduct() {
        ProductRequest request = new ProductRequest(
                "X147",
                "Bread",
                9.90,
                ProductCategory.FOOD,
                10
        );
        Product mappedProduct = new Product(
                "X147",
                "Bread",
                9.90,
                ProductCategory.FOOD,
                10
        );
        when(mapper.toProduct(request)).thenReturn(mappedProduct);
        when(repository.save(mappedProduct)).thenReturn(mappedProduct);

        String result = service.addProduct(request);

        verify(mapper).toProduct(request);
        verify(repository).save(mappedProduct);

        assertEquals("X147", result);

    }

    @Test
    void getProduct() {
        Product product = Product.builder()
                .productId("X159")
                .productName("Apple")
                .price(1.20)
                .category(ProductCategory.FOOD)
                .quantity(50)
                .build();
        Product savedproduct = repository.save(product);
        when(savedproduct).thenReturn(product);
        List<ProductResponse> result = service.getProduct();

        assertEquals(1, result.size());

    }
}
