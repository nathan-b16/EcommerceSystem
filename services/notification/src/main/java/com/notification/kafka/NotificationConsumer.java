package com.notification.kafka;


import com.notification.Email.EmailService;
import com.notification.kafka.order.OrderConfirmation;
import com.notification.kafka.payment.PaymentConfirmation;
import com.notification.notification.Notification;
import com.notification.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.notification.notification.NotificationType.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation confirmation) throws MessagingException {
        repository.save(
                Notification.builder()
                        .notificationType(PAYMENT_NOTIFICATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(confirmation)
                        .build()
        );

        var customerName = confirmation.customerFirstname() + " " + confirmation.customerLastname();
        emailService.sendPaymentSuccessEmail(
                confirmation.customerEmail(),
                customerName,
                confirmation.amount(),
                confirmation.orderReference()
        );
    }


    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation confirmation) throws MessagingException {
        repository.save(
                Notification.builder()
                        .notificationType(ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(confirmation)
                        .build()
        );
        var customerName = confirmation.customer().getFirstname() + " " + confirmation.customer().getLastname();
        emailService.sendOrderConfirmationEmail(
                confirmation.customer().getEmail(),
                customerName,
                confirmation.totalAmount(),
                confirmation.orderReference(),
                confirmation.procductList()
        );
    }

}
