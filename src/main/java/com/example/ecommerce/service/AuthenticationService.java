package com.example.ecommerce.service;

import com.example.ecommerce.config.app.RegexValidation;
import com.example.ecommerce.constant.ApplicationConstant;
import com.example.ecommerce.dto.AuthRequestDto;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.InvalidFormatException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.exception.UnAuthorizedException;
import com.example.ecommerce.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import static java.security.KeyRep.Type.SECRET;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private static final String BEARER_PREFIX = "Bearer ";
    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public ResponseEntity<String> authentication(AuthRequestDto authRequest) {
        if (!(RegexValidation.isValidEmail(authRequest.getUsername()) && RegexValidation.isValidPasswword(authRequest.getPassword()))) {
            throw new InvalidFormatException("Geçersiz format");
        }

        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(authRequest.getUsername(), authRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);

        if (authenticate.isAuthenticated()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(ApplicationConstant.JWT_HEADER, generateJwt(authenticate))
                    .body(authenticate.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(", "))); // Roller body'ye yazılıyor
        } else {
            throw new NotFoundException("Wrong information");
        }
    }


    public String generateJwt(Authentication authenticate){
        String secret = ApplicationConstant.JWT_SECRET_DEFAULT_VALUE;
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setIssuer("OneUserOneAdmin")
                .setSubject("Jwt Token")
                .claim("username", authenticate.getName())
                .claim("authorities",authenticate.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date((new java.util.Date()).getTime() + 300000000))  // 80 saat civarı
                .signWith(secretKey).compact();// compact methodu str değeri olarak çıkartmayı sağlar.

    }


    public boolean isTokenExpired(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(ApplicationConstant.JWT_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String substring = authorizationHeader.substring(BEARER_PREFIX.length());

            Claims claims = getClaims(substring);
            return claims.getExpiration().before(new Date());
        }
        throw new InvalidFormatException("Token is not access");
    }
    public Claims getClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(ApplicationConstant.JWT_SECRET_DEFAULT_VALUE.getBytes(StandardCharsets.UTF_8));
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // Token'in payload kısmını döndürür.
        }catch (ExpiredJwtException e){
            throw new InvalidFormatException("Token is expired");
        }
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return userService.getUser(authentication.getName());
        }else
            throw new UnAuthorizedException("USer not authorized");

    }
}
