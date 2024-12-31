package com.example.ecommerce.config.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidation {


    public static boolean isValidEmail(String email){
        email.trim();
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = pattern.matcher(email);

        return emailMatcher.matches();
    }

    public static boolean isValidPasswword(String password){
        password.trim();
        String PASSWORD_REGEX = "^(?=.*[A-Z]).{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passwordMatcher = pattern.matcher(password);

        return passwordMatcher.matches();
    }


    public static boolean isValidPhoneNumber(String phoneNumber) {
        phoneNumber.trim();
        String PHONE_NUMBER_REGEX = "^\\d{10}$";
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher passwordMatchers = pattern.matcher(phoneNumber);

        return passwordMatchers.matches();
    }

    public static boolean isFourDigitNumber(String input) {
        String regex = "^\\d{4}$";
        return input.matches(regex);
    }




}
