package com.example.ecommerce.service.user;

import com.example.ecommerce.builder.user.AdminBuilder;
import com.example.ecommerce.config.app.RegexValidation;
import com.example.ecommerce.dto.AdminResponseDto;
import com.example.ecommerce.dto.user.AdminRequestDto;
import com.example.ecommerce.dto.user.AdminUpdateDto;
import com.example.ecommerce.entity.user.Admin;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.exception.ResourceAlreadyExistException;
import com.example.ecommerce.repository.user.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminBuilder adminBuilder;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, AdminBuilder adminBuilder, UserService userService, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.adminBuilder = adminBuilder;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Admin oluşturma
    public AdminResponseDto createAdmin(AdminRequestDto adminRequestDto) {

        if (adminRequestDto == null)
            throw new BadRequestException("Resource Not Exist");

        if (userService.isExistUser(adminRequestDto.getUsername()))
            throw new ResourceAlreadyExistException("User already exists");

        if (!RegexValidation.isValidEmail(adminRequestDto.getUsername()))
            throw new BadRequestException("Invalid Username");

        if (!RegexValidation.isValidPasswword(adminRequestDto.getPassword()))
            throw new BadRequestException("Invalid Password");

        Admin admin = adminBuilder.adminRequestDtoToAdmin(adminRequestDto);
        admin.setPassword(passwordEncoder.encode(adminRequestDto.getPassword()));

        return adminBuilder.adminToAdminResponseDto(adminRepository.save(admin));
    }

    // Admin güncelleme
    public AdminResponseDto updateAdmin(AdminUpdateDto updatedAdmin, int id) {
        Admin admin = getAdminById(id);

        admin.setFirstName(updatedAdmin.getFirstName());
        admin.setLastName(updatedAdmin.getLastName());

        return adminBuilder.adminToAdminResponseDto(adminRepository.save(admin)); // veya Exception fırlatılabilir
    }

    // Admin'i ID ile bulma
    public Admin getAdminById(int id) {
        return adminRepository.findById(id).orElseThrow(()-> new NotFoundException("Admin Not Found"));
    }

    // Admin'i kullanıcı adı ile bulma
    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Admin Not Found"));
    }

    // Tüm Admin'leri listeleme
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }



    // Admin silme
    public boolean deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
