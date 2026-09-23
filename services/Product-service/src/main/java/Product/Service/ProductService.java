package Product.Service;

import Product.Exception.ProductNotFoundException;
import Product.Model.*;
import Product.Repository.ProductRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;


    public List<ProductResponse> getProduct() {
        return repository.findAll()
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(String productId) {
        return repository.findById(productId)
                .map(mapper::fromEntity)
                .orElseThrow(()-> new ProductNotFoundException(
                        format("The product you are looking for is not found: %s", productId)
                ));
    }

    public String addProduct(ProductRequest request) {
        Product product = repository.save(mapper.toProduct(request));
        return product.getProductId();
    }

    public List<ProductResponse> getProductByCategory(ProductCategory category) {
        return repository.findAll()
                .stream()
                .filter(c->c.getCategory().equals(category))
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

    public void deleteProductById(String productId) {
        if (!repository.existsById(productId)) {
            throw new ProductNotFoundException("No product found with id " + productId);
        }
        repository.deleteById(productId);
    }

    public void updateProduct(ProductRequest request) {
        Product product = repository.findById(request.productId())
                .orElseThrow(()-> new ProductNotFoundException(
                        format("ProductID: %s was not found", request.productId())
                ));
        mergeProduct(product, request);
        repository.save(product);
    }

    public List<PurchaseProductResponse> purchaseProducts(List<PurchaseProductRequest> request) {
        var productsIds = request.stream().map(PurchaseProductRequest::productId).distinct().toList();
        var productsInStock = repository.findAllById(productsIds);

        if(productsIds.size() != productsInStock.size()) {
            throw new ProductNotFoundException("One or more were not found");
        }

        Map<String, Product> productsById = new HashMap<>(); // We map the product by their IDs
        for(Product product: productsInStock){
            productsById.put(product.getProductId(), product);
        }

        var purchased = new ArrayList<PurchaseProductResponse>();
        for(PurchaseProductRequest req : request){
            var product = productsById.get(req.productId());
            if(req.quantity() > product.getQuantity()){
                throw new ProductNotFoundException("Short in stock");
            }
            product.setQuantity(product.getQuantity() - req.quantity());
            repository.save(product);
            purchased.add(new PurchaseProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    product.getPrice(),
                    product.getCategory(),
                    product.getQuantity()
            ));
        }
        return purchased;
    }

    private void mergeProduct(Product product, ProductRequest request){
        if(StringUtils.isNotBlank(request.productName())){
            product.setProductName(request.productName());
        }
        if(StringUtils.isNotBlank(String.valueOf(request.price()))){
            product.setPrice(request.price());
        }
        if(request.quantity() != null){
            product.setQuantity(request.quantity());
        }
        if(request.category() != null){
            product.setCategory((request.category()));
        }
    }
}

