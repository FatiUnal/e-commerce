package com.example.ecommerce.service.message.sms;

public interface SmsProvider {
    String sendSms(String phoneNumber, String message);
}
