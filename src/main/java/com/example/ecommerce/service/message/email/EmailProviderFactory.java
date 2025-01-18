package com.example.ecommerce.service.message.email;

import com.example.ecommerce.service.message.sms.SmsProvider;
import com.example.ecommerce.service.message.sms.TwilioSmsProvider;

import java.util.Map;

public class EmailProviderFactory {

    public static EmailProvider getProvider(String providerName, Map<String, String> config) {
        switch (providerName) {
            case "GMAIL":
                return new GmailProvider(
                        config.get("apiKey"),
                        config.get("apiSecret"),
                        config.get("fromEmail")
                );
            case "NEXMO":
                System.out.println("nexo");
            default:
                throw new IllegalArgumentException("Desteklenmeyen sağlayıcı: " + providerName);
        }
    }


}
