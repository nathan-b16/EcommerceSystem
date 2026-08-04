package com.Payment.payment;

import java.math.BigDecimal;

public record PaymentRequest(
        String id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
