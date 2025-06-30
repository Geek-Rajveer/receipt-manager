package com.receiptmanage.receipt_manager.controller;

import com.receiptmanage.receipt_manager.model.User;
import com.receiptmanage.receipt_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            return "❌ Email already registered!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "✅ User registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            if (passwordEncoder.matches(user.getPassword(), existing.get().getPassword())) {
                return "✅ Login successful!";
            }
        }
        return "❌ Invalid email or password.";
    }
}
