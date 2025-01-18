package com.example.ecommerce.controller;

import com.example.ecommerce.dto.user.CustomerRequestDto;
import com.example.ecommerce.entity.user.Customer;
import com.example.ecommerce.service.user.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/email")
    public ResponseEntity<String> createCustomerWithEmailPassword(@RequestBody CustomerRequestDto customerRequestDto) {
        return new ResponseEntity<>(customerService.createCustomerWithEmailPassword(customerRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/phone")
    public ResponseEntity<String> createCustomerWithPhoneNumber(@RequestBody CustomerRequestDto customerRequestDto) {
        return new ResponseEntity<>(customerService.createCustomerWithPhoneNumber(customerRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String username,@RequestParam String otp) {
        return new ResponseEntity<>(customerService.verifyOtp(username,otp),HttpStatus.OK);
    }

    @GetMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String username) {
        return new ResponseEntity<>(customerService.sendOtp(username),HttpStatus.OK);
    }

    @GetMapping("/find-user")
    public ResponseEntity<Customer> findUsername(@RequestParam String username) {
        return new ResponseEntity<>(customerService.findUser(username),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCustomer(@RequestParam String username) {
        return new ResponseEntity<>(customerService.deleteCustomer(username),HttpStatus.OK);
    }

}
