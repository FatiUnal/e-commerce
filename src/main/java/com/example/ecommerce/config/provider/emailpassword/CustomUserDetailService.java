package com.example.ecommerce.config.provider.emailpassword;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository usersRepository;

    public CustomUserDetailService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User not found"));
        System.out.println("authorities = " + user.getAuthorities());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities());
    }
}
