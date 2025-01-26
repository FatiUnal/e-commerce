package com.example.ecommerce.filter;

import com.example.ecommerce.constant.ApplicationConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(ApplicationConstant.JWT_HEADER);
        if (jwt != null && jwt.startsWith("Bearer "))
            jwt = jwt.substring(7);

        if (jwt != null){
            try{
                String secret = ApplicationConstant.JWT_SECRET_DEFAULT_VALUE;
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                response.setHeader(ApplicationConstant.JWT_HEADER,jwt);
                if (secretKey != null){
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(secretKey) // Secret key eklenir
                            .build() // JwtParser oluşturulur
                            .parseClaimsJws(jwt) // Token doğrulanır
                            .getBody(); // Token içeriği alınır
                    String username = String.valueOf(claims.get("username"));
                    List<String> authoritiesList = claims.get("authorities", List.class);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,null,
                            authoritiesList.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())
                            );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (Exception e){
                throw new BadCredentialsException("Invalid Token received");
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/user/auth");
    }
}
