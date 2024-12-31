package com.example.ecommerce.repository.user;

import com.example.ecommerce.entity.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
    boolean existsById(int id);
}
