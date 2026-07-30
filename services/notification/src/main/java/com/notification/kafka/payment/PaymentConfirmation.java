package com.notification.kafka.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

public record PaymentConfirmation (

     String orderReference,
     BigDecimal amount,
     PaymentMethod paymentMethod,
     String customerFirstname,
     String customerLastname,
     String customerEmail
) {

}
