package com.Order.DTO;

import com.Order.Product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest (
    Integer Id,
    @NotNull(message = "customer id shouldn't null")
    @NotEmpty(message = "customer id shouldn't be blank")
    @NotBlank()
    String customerId,
    String reference,
    @Positive(message = "order amount should be positive")
    BigDecimal totalAmount,
    @NotNull(message = "payment method should be selected")
    PaymentMethod paymentMethod,
    @NotEmpty(message = "You must purchase at least 1 product")
    List<PurchaseRequest> products

){}
