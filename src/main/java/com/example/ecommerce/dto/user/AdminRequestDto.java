package com.example.ecommerce.dto.user;

import jakarta.persistence.Column;

public class AdminRequestDto {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String username;
    private String password;

    public AdminRequestDto(String firstName, String lastName, String phoneNo, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
