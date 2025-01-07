package com.example.ecommerce.service.user;

import com.example.ecommerce.dto.user.CustomerRequestDto;
import com.example.ecommerce.entity.user.Customer;
import com.example.ecommerce.repository.user.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerRequestDto customerRequestDto) {

    }


}
