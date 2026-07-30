package com.Order.Product;

public record PurchaseRequest(
        String productId,
        Integer quantity
) {
}
