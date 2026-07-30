package com.Order.Customer;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
