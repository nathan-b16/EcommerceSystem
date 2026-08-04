package Product.Controller;


import Product.Model.ProductRequest;
import Product.Model.ProductResponse;
import Product.Model.PurchaseProductRequest;
import Product.Model.PurchaseProductResponse;
import Product.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @PostMapping("/purchase")
    public ResponseEntity<List<PurchaseProductResponse>> purchaseProducts(@RequestBody List<PurchaseProductRequest> request) { // todo
        return ResponseEntity.ok(service.purchaseProducts(request));
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
