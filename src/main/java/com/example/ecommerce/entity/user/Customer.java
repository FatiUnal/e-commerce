package com.example.ecommerce.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Set;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
    private String address;
    private boolean active;

    public Customer(String firstName, String lastName, String phoneNo, String username, String password, String address) {
        super(firstName, lastName, phoneNo, username, password, Set.of(Roles.ROLE_CUSTOMER));
        this.address = address;
    }

    public Customer() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
