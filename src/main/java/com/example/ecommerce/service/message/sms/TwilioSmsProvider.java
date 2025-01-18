package com.example.ecommerce.service.message.sms;

public class TwilioSmsProvider implements SmsProvider {
    private String apiKey;
    private String apiSecret;
    private String fromNumber;

    public TwilioSmsProvider(String apiKey, String apiSecret, String fromNumber) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.fromNumber = fromNumber;
    }

    @Override
    public String sendSms(String phoneNumber, String otp) {
        System.out.println("otp: "+otp);
        return "success sms";
    }
}
