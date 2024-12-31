package com.example.ecommerce.repository.user;

import com.example.ecommerce.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
