package com.SummerProject.Ecommerce.Product.Repository;

import com.SummerProject.Ecommerce.Product.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
