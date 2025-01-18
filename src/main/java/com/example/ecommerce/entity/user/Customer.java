package com.example.ecommerce.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Set;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
    private String address;

    @Enumerated(EnumType.STRING)
    private RegisterType registerType;

    public Customer(String firstName, String lastName, String username, String password, boolean enabled, boolean accountNonLocked, RegisterType registerType) {
        super(firstName, lastName, null, username, password, Set.of(Roles.ROLE_CUSTOMER),enabled,accountNonLocked);
        this.registerType = registerType;
    }

    public Customer() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RegisterType getRegisterType() {
        return registerType;
    }

    public void setRegisterType(RegisterType registerType) {
        this.registerType = registerType;
    }
}
