package com.example.ecommerce.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Set;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin(String firstName, String lastName, String phoneNo, String username, String password,boolean enabled,boolean accountNonLocked) {
        super(firstName, lastName, phoneNo, username, password, Set.of(Roles.ROLE_ADMIN),enabled,accountNonLocked);
    }

    public Admin() {

    }
}
