package com.example.ecommerce.entity.user.auth;

import com.example.ecommerce.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "otps")
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String otp;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime created;
    private LocalDateTime expired;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isValid = true;

    public Otp(String otp, User user) {
        this.otp = otp;
        this.user = user;
        this.created = LocalDateTime.now();
        this.expired = LocalDateTime.now().plusMinutes(10);
    }

    public Otp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
