package com.SummerProject.Ecommerce.Product.Service;


import com.SummerProject.Ecommerce.Product.Model.Product;
import com.SummerProject.Ecommerce.Product.Model.ProductRequest;
import com.SummerProject.Ecommerce.Product.Model.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);

    ProductResponse fromEntity(Product product);

    List<ProductResponse> getAllProducts(ProductRequest productRequest);


}
