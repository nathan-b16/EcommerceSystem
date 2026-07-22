package com.SummerProject.Ecommerce.Product.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Product {

    private String productName;
    private String productId;
    private double price;
    private ProductCategory category;
    private Integer quantity;
}
