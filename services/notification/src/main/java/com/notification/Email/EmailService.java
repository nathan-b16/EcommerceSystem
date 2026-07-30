package com.notification.Email;

import com.notification.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.notification.Email.EmailTemplates.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
            String destEmail,
            String customerName,
            BigDecimal amount,
            String orderRefrence
    ) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("CustomerService@email.com");

        final String TemplateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables =  new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderRefrence);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(TemplateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destEmail);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.warn("Cannot send email to {}", destEmail);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destEmail,
            String customerName,
            BigDecimal amount,
            String orderRefrence,
            List<Product> productList
    ) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("CustomerService@email.com");

        final String TemplateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables =  new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("TotalAount", amount);
        variables.put("orderReference", orderRefrence);
        variables.put("products", productList);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(TemplateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destEmail);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.warn("Cannot send email to {}", destEmail);
        }
    }
}
