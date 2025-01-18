package com.example.ecommerce.config;

import com.example.ecommerce.config.provider.emailpassword.CustomUserDetailService;
import com.example.ecommerce.config.provider.emailpassword.UsernamePasswordAuthenticationProvider;
import com.example.ecommerce.exception.authentication.CustomAuthenticationEntryPoint;
import com.example.ecommerce.filter.JwtValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailService userDetailsService;
    private final JwtValidationFilter jwtValidationFilter;

    public SecurityConfig(CustomUserDetailService userDetailsService, JwtValidationFilter jwtValidationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtValidationFilter = jwtValidationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.GET,"/api/v1/auth/user").authenticated()
                        .anyRequest().permitAll())
                .exceptionHandling(x -> x.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager defaultAuthManager = authenticationConfiguration.getAuthenticationManager();

        List<AuthenticationProvider> customProviders = List.of(
                new UsernamePasswordAuthenticationProvider(userDetailsService, passwordEncoder())
        );

        return new ProviderManager(customProviders) {
            @Override
            public Authentication authenticate(Authentication authentication) {
                try {
                    return super.authenticate(authentication);
                } catch (Exception e) {
                    return defaultAuthManager.authenticate(authentication);
                }
            }
        };
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration ccfg = new CorsConfiguration();
            ccfg.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:5173"));
            ccfg.setAllowedMethods(Collections.singletonList("*"));
            ccfg.setAllowCredentials(true);
            ccfg.setAllowedHeaders(Collections.singletonList("*"));
            ccfg.setExposedHeaders(Arrays.asList("Authorization"));
            ccfg.setMaxAge(3600L);
            return ccfg;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
