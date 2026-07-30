package com.Order.Orderline;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        String productId,
        Integer quantity
) {
}
