package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AuthRequestDto;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.service.AuthenticationService;
import com.example.ecommerce.service.user.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @GetMapping("/user")
    public ResponseEntity<User> getUser(){
        return new ResponseEntity<>(authenticationService.getUser(),HttpStatus.OK);
    }


    @GetMapping("/login-success")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        // Process user information and customize your logic
        System.out.println("giriş yapıldı: "+ principal.getName());
        return "asd";
    }


}
