package com.java.Linkly.service;

import com.java.Linkly.entity.User;
import com.java.Linkly.model.CreateUserCmd;
import com.java.Linkly.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return userRepo.findByEmail(email).orElse(null);
        }
        return null;
    }

    public Long getCurrentUserId() {
        User user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

    @Transactional
    public void createUser(CreateUserCmd cmd) {
        if(userRepo.existsByEmail(cmd.email()))
            throw new RuntimeException("Email already exists");
        var user = new User();
        user.setEmail(cmd.email());
        user.setPassword(passwordEncoder.encode(cmd.password()));
        user.setName(cmd.name());
        user.setRole(cmd.role());
        user.setCreatedAt(Instant.now());
        userRepo.save(user);
    }
}
