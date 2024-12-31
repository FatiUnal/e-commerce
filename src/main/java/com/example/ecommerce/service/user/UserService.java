package com.example.ecommerce.service.user;

import com.example.ecommerce.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isExistUser(String username){
        return userRepository.existsByUsername(username);
    }
}
