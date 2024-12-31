package com.example.ecommerce.repository.user;

import com.example.ecommerce.entity.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findById(Integer id);
    boolean existsById(int id);
}
