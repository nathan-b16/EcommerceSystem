package Product.Service;

import Product.Exception.ProductNotFoundException;
import Product.Model.*;
import Product.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;
    @Mock
    private ProductMapper mapper;
    @InjectMocks
    private ProductService service;

    @Test
    void addProduct(){
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
        ProductResponse mapped_product = new ProductResponse(
                "X159",
                "Apple",
                1.20,
                ProductCategory.FOOD,
                50
                );
        when(repository.findAll()).thenReturn(List.of(product));
        when(mapper.fromEntity(product)).thenReturn(mapped_product);

        List<ProductResponse> result = service.getProduct();

        assertEquals(1, result.size());
    }

    @Test
    void getProductById() {
        Product product = Product.builder()
                .productId("Test-ID-4")
                .productName("Iphone")
                .price(2500)
                .category(ProductCategory.ELECTRONIC)
                .quantity(50)
                .build();

        ProductResponse mapped_product = new ProductResponse(
                "Test-ID-4",
                "Iphone",
                2500,
                ProductCategory.ELECTRONIC,
                50
        );

        when(repository.findById(product.getProductId())).thenReturn(Optional.of(product));
        when(mapper.fromEntity(product)).thenReturn(mapped_product);

        ProductResponse productResponse = service.getProductById("Test-ID-4");

        assertEquals("Test-ID-4", productResponse.Id());
        assertEquals(mapped_product, productResponse);
        verify(repository).findById("Test-ID-4");
        verify(mapper).fromEntity(product);
    }

    @Test
    void getProductByCategory() {
        Product product = Product.builder()
                .productId("Test-ID-4")
                .productName("Iphone")
                .price(2500)
                .category(ProductCategory.ELECTRONIC)
                .quantity(50)
                .build();

        ProductResponse mapped_product = new ProductResponse(
                "Test-ID-4",
                "Iphone",
                2500,
                ProductCategory.ELECTRONIC,
                50
        );

        when(repository.findAll()).thenReturn(List.of(product));
        when(mapper.fromEntity(product)).thenReturn(mapped_product);

        List<ProductResponse> productResponse = service.getProductByCategory(ProductCategory.ELECTRONIC);

        assertEquals(ProductCategory.ELECTRONIC, productResponse.getFirst().categoryId());
    }

    @Test
    void deleteProductById_existing_product() {
        String productId = "Test-ID-6";

        when(repository.existsById(productId)).thenReturn(true);

        service.deleteProductById(productId);

        verify(repository).existsById(productId);
        verify(repository).deleteById(productId);
    }

    @Test
    void deleteProductById_NOT_existing_product() {
        String productId = "noID";

        when(repository.existsById(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class,
                ()-> service.deleteProductById(productId));

        verify(repository, never()).deleteById(any());
    }

    @Test
    void updateProduct_existingProduct() {
        Product product = Product.builder()
                .productId("Test-ID-6")
                .productName("Old Name")
                .price(100.0)
                .category(ProductCategory.FOOD)
                .quantity(5)
                .build();

        ProductRequest request = new ProductRequest(
                "Test-ID-6",
                "New Name",
                200.0,
                ProductCategory.ELECTRONIC,
                10
        );
        when(repository.findById(product.getProductId())).thenReturn(Optional.of(product));

        service.updateProduct(request);

        assertEquals("New Name", product.getProductName());
        assertEquals(200.0, product.getPrice());
        assertEquals(ProductCategory.ELECTRONIC, product.getCategory());
        assertEquals(10, product.getQuantity());

        verify(repository).findById("Test-ID-6");

    }

    @Test
    void updateProduct_nonExistingProduct_throwsException() {
        ProductRequest request = new ProductRequest(
                "missing",
                "New Name",
                200.0,
                ProductCategory.ELECTRONIC,
                10
        );

        when(repository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> service.updateProduct(request));
    }

    @Test
    void purchaseProducts() {
        PurchaseProductRequest reqBread = new PurchaseProductRequest("X147", 2);
        PurchaseProductRequest reqApple = new PurchaseProductRequest("X159", 5);

        Product bread = Product.builder()
                .productId("X147")
                .productName("Bread")
                .price(9.90)
                .category(ProductCategory.FOOD)
                .quantity(10)
                .build();

        Product apple = Product.builder()
                .productId("X159")
                .productName("Apple")
                .price(1.20)
                .category(ProductCategory.FOOD)
                .quantity(50)
                .build();

        when(repository.findAllById(List.of("X147", "X159")))
                .thenReturn(List.of(bread, apple));

        List<PurchaseProductResponse> result = service.purchaseProducts(List.of(reqBread, reqApple));

        assertEquals(2, result.size());

        assertEquals("X147", result.get(0).productId());
        assertEquals(8, result.get(0).quantity());

        assertEquals("X159", result.get(1).productId());
        assertEquals(45, result.get(1).quantity());

    }
}