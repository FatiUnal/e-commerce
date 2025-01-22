package com.example.ecommerce.config.provider.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String name = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");
        String picture = oauth2User.getAttribute("picture");

        // JWT token oluşturma (Örneğin, bir servis üzerinden token oluşturulabilir)
        String token = "your_generated_jwt_token"; // Buraya JWT token üretim kodu ekleyin.

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
    }
}
