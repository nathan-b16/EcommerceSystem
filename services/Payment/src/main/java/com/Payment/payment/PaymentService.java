package com.Payment.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public String createPayment(PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));
        return payment.getId();
    }
}
