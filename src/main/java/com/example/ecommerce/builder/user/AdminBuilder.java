package com.example.ecommerce.builder.user;

import com.example.ecommerce.dto.user.AdminRequestDto;
import com.example.ecommerce.entity.user.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminBuilder {
    public Admin AdminRequestDtoToAdmin(AdminRequestDto adminRequestDto) {
        return new Admin(
                adminRequestDto.getFirstName(),
                adminRequestDto.getLastName(),
                adminRequestDto.getPhoneNo(),
                adminRequestDto.getUsername(),
                adminRequestDto.getPassword()
        );
    }
}
