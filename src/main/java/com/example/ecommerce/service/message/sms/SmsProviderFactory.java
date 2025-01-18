package com.example.ecommerce.service.message.sms;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsProviderFactory {


    public static SmsProvider getProvider(String providerName, Map<String, String> config) {
        switch (providerName) {
            case "TWILIO":
                return new TwilioSmsProvider(
                        config.get("apiKey"),
                        config.get("apiSecret"),
                        config.get("fromNumber")
                );
            case "NEXMO":
                System.out.println("nexo");
            default:
                throw new IllegalArgumentException("Desteklenmeyen sağlayıcı: " + providerName);
        }
    }

}
