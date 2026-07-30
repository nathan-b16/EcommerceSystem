package com.Order.kafka;

import com.Order.Customer.CustomerResponse;
import com.Order.DTO.PaymentMethod;
import com.Order.Product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (

        String orderId,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse response,
        List<PurchaseResponse> products
) {
}
