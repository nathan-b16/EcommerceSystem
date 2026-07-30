package com.notification.Email;


import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment_Successfully_processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order_Confirmation")
    ;

    @Getter
    private final String template;

    @Getter
    private final String Subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        Subject = subject;
    }
}
