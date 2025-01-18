package com.example.ecommerce.service.message.email;

public class GmailProvider implements EmailProvider{
    private String apiKey;
    private String apiSecret;
    private String fromEmail;

    public GmailProvider(String apiKey, String apiSecret, String fromEmail) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.fromEmail = fromEmail;
    }

    @Override
    public String sendEmail(String to, String subject, String body) {
        System.out.println("to: "+to+", subject: "+subject+", body: "+body);
        return "success email";
    }
}
