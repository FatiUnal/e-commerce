package com.example.ecommerce.builder.user;

import com.example.ecommerce.dto.user.CustomerRequestDto;
import com.example.ecommerce.entity.user.Customer;
import com.example.ecommerce.entity.user.RegisterType;
import org.springframework.stereotype.Component;

@Component
public class CustomerBuilder {
    public Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto, RegisterType registerType) {
        return new Customer(
                customerRequestDto.getFirstName(),
                customerRequestDto.getLastName(),
                customerRequestDto.getUsername(),
                customerRequestDto.getPassword(),
                false,
                false,
                registerType
        );
    }
}
