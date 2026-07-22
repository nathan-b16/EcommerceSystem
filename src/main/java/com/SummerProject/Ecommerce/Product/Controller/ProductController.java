package com.SummerProject.Ecommerce.Product.Controller;


import com.SummerProject.Ecommerce.Product.Model.ProductRequest;
import com.SummerProject.Ecommerce.Product.Model.ProductResponse;
import com.SummerProject.Ecommerce.Product.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProduct()
    {
        return ResponseEntity.ok(service.getProduct());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductByID(@PathVariable String productId) {
        return ResponseEntity.ok(service.getProductById(productId));
    }
    @GetMapping("/{category}")
    public ResponseEntity<List<ProductResponse>> getProductByCategory(@PathVariable Enum category){
        return ResponseEntity.ok(service.getProductByCategory(category));
    }


    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductRequest request){
        return ResponseEntity.ok(service.addProduct(request));
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductRequest request){
        service.updateProduct(request);
        return ResponseEntity.accepted().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId){
        service.deleteProductById(productId);
        return ResponseEntity.accepted().build();
    }
}
