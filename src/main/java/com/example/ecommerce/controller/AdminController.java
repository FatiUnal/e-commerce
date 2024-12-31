package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AdminResponseDto;
import com.example.ecommerce.dto.user.AdminRequestDto;
import com.example.ecommerce.dto.user.AdminUpdateDto;
import com.example.ecommerce.entity.user.Admin;
import com.example.ecommerce.service.user.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody AdminRequestDto admin) {
        return new ResponseEntity<>(adminService.createAdmin(admin), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AdminResponseDto> updateAdmin(@RequestBody AdminUpdateDto admin, @RequestParam int adminId){
        return new ResponseEntity<>(adminService.updateAdmin(admin,adminId),HttpStatus.OK);
    }

}
