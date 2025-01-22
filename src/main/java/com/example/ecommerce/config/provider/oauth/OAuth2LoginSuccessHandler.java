package com.example.ecommerce.config.provider.oauth;

import com.example.ecommerce.service.user.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private CustomerService customerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String name = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");
        String picture = oauth2User.getAttribute("picture");

        System.out.println("name: " + name);
        System.out.println("email: " + email);
        System.out.println("picture: " + picture);


        String token = "your_generated_jwt_token";

        // JSON formatında yanıt dön
        Map<String, String> userResponse = Map.of(
                "name", name,
                "email", email,
                "picture", picture,
                "token", token
        );

        // JSON döndür
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(userResponse));
        response.sendRedirect("http://localhost:5173/login-redirect?token=" + token);  // Token'ı frontend'e yönlendirme
    }
}
