package Product.Service;

import Product.Exception.ProductNotFoundException;
import Product.Model.Product;
import Product.Model.ProductRequest;
import Product.Model.ProductResponse;
import Product.Repository.ProductRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<?> purchaseProducts(List<PurchaseProductRequest> request) {

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

