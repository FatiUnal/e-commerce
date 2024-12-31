package com.example.ecommerce.service.user;

import com.example.ecommerce.builder.user.AdminBuilder;
import com.example.ecommerce.config.app.RegexValidation;
import com.example.ecommerce.dto.user.AdminRequestDto;
import com.example.ecommerce.entity.user.Admin;
import com.example.ecommerce.exception.BadRequestException;
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
    public Admin createAdmin(AdminRequestDto adminRequestDto) {

        if (adminRequestDto == null)
            throw new BadRequestException("Resource Not Exist");

        if (userService.isExistUser(adminRequestDto.getUsername()))
            throw new ResourceAlreadyExistException("User already exists");

        if (!RegexValidation.isValidEmail(adminRequestDto.getUsername()))
            throw new BadRequestException("Invalid Username");

        if (!RegexValidation.isValidPasswword(adminRequestDto.getPassword()))
            throw new BadRequestException("Invalid Password");

        Admin admin = adminBuilder.AdminRequestDtoToAdmin(adminRequestDto);
        admin.setPassword(passwordEncoder.encode(adminRequestDto.getPassword()));

        return adminRepository.save(admin);
    }

    // Admin'i ID ile bulma
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    // Admin'i kullanıcı adı ile bulma
    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    // Tüm Admin'leri listeleme
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Admin güncelleme
    public Admin updateAdmin(int id, Admin updatedAdmin) {
        if (adminRepository.existsById(id)) {
            updatedAdmin.setId(id);
            return adminRepository.save(updatedAdmin);
        }
        return null; // veya Exception fırlatılabilir
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
