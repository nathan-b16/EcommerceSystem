package Product.Service;

import Product.Exception.ProductNotFoundException;
import Product.Model.*;
import Product.Repository.ProductRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public  ProductResponse getProductById(String productId) {
        if(productId == null) return null;
        return repository.findById(productId)
                .map(mapper::fromEntity)
                .orElseThrow(()-> new ProductNotFoundException(
                        format("The product you are looking for is not found", productId)
                ));
    }

    public String addProduct(ProductRequest request) {
        Product product = repository.save(mapper.toProduct(request));
        return product.getProductId();
    }

    public List<ProductResponse> getProductByCategory(Enum category) {
        return repository.findAll()
                .stream()
                .filter(c->c.getCategory().equals(category))
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

    public void deleteProductById(String productId) {
        repository.deleteById(productId);
    }

    public void updateProduct(ProductRequest request) {
        Product product = repository.findById(request.productId())
                .orElseThrow(()-> new ProductNotFoundException(
                        format("ProductID: %s was not found", request.productId())
                ));
        mergeProduct(product, request);
    }

    public List<PurchaseProductResponse> purchaseProducts(List<PurchaseProductRequest> request) {
        var productsIds = request.stream().map(PurchaseProductRequest::productId).distinct().toList();
        var productsInStock = repository.findAllById(productsIds);
        var purchased = new ArrayList<PurchaseProductResponse>();

        if(productsIds.size() != productsInStock.size()) {
            throw new ProductNotFoundException("One or more were not found");
        }
        for(int i = 0; i<productsIds.size(); i++) {
            var product = productsInStock.get(i);
            if(request.get(i).quantity() > productsInStock.get(i).getQuantity()) {
                throw new ProductNotFoundException("Short in stock");
            }
            var updatedQuantity= product.getQuantity() - request.get(i).quantity();
            product.setQuantity(updatedQuantity);
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
        if(StringUtils.isNotBlank(String.valueOf(request.quantity()))){
            product.setQuantity(request.quantity());
        }
        if(StringUtils.isNotBlank(String.valueOf(request.category()))){
            product.setCategory((request.category()));
        }
    }
}

