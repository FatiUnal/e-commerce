package com.example.ecommerce.service.user;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isExistUserByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean isExistUserByPhone(String phone){
        return userRepository.existsByPhoneNo(phone);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new NotFoundException("User not found"));
    }
}
