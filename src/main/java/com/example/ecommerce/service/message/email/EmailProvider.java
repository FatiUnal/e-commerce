package com.example.ecommerce.service.message.email;

public interface EmailProvider {
    String sendEmail(String to, String subject, String body);
}
