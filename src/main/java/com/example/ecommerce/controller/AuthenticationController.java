package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AuthRequestDto;
import com.example.ecommerce.service.AuthenticationService;
import com.example.ecommerce.service.user.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

    }

    @PostMapping
    public ResponseEntity<String> authEmailPassword(@RequestBody AuthRequestDto authRequest){
        return authenticationService.authentication(authRequest);
    }

    @GetMapping("/expired")
    public ResponseEntity<Boolean> authExpired(HttpServletRequest request){
        return new ResponseEntity<>(authenticationService.isTokenExpired(request), HttpStatus.OK);
    }




}
