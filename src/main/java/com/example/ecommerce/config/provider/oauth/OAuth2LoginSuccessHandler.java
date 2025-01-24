package com.example.ecommerce.config.provider.oauth;

import com.example.ecommerce.constant.ApplicationConstant;
import com.example.ecommerce.entity.user.Customer;
import com.example.ecommerce.entity.user.Roles;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.service.user.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final CustomerService customerService;

    public OAuth2LoginSuccessHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String name = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");
        String picture = oauth2User.getAttribute("picture");

        System.out.println("name: " + name);
        System.out.println("email: " + email);
        System.out.println("picture: " + picture);

        if (customerService.isExistUser(email)){
            if (customerService.isExistCustomer(email)){
                Customer customer = customerService.findByUsername(email);
                String token = generateJwt(customer.getUsername(),customer.getAuthorities());
                response.sendRedirect("http://localhost:5173/login-redirect?token=" + token);  // Token'ı frontend'e yönlendirme
            }else
                throw new BadRequestException("Geçersiz kullanıcı");

        }else{
            Customer customerWithGoogle = customerService.createCustomerWithGoogle(name, email);
            String token = generateJwt(customerWithGoogle.getUsername(),customerWithGoogle.getAuthorities());
            response.sendRedirect("http://localhost:5173/login-redirect?token=" + token);  // Token'ı frontend'e yönlendirme
        }

    }

    public String generateJwt(String username, Set<Roles> authorities) {
        String secret = ApplicationConstant.JWT_SECRET_DEFAULT_VALUE;
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setIssuer("OneUserOneAdmin")
                .setSubject("Jwt Token")
                .claim("username", username)
                .claim("authorities",authorities.stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date((new java.util.Date()).getTime() + 300000000))  // 80 saat civarı
                .signWith(secretKey).compact();// compact methodu str değeri olarak çıkartmayı sağlar.

    }
}
