package Product.Service;


import Product.Model.Product;
import Product.Model.ProductRequest;
import Product.Model.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);

    ProductResponse fromEntity(Product product);

    List<ProductResponse> getAllProducts(List<Product> products);


}
