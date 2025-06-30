package com.receiptmanage.receipt_manager.controller;

import com.receiptmanage.receipt_manager.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    public String testEmail() {
        // Change to your actual email for testing
        String to = "rajveersengar03@gmail.com";
        String product = "Test Product";
        String expiry = "2025-07-01";

        emailService.sendWarrantyExpiryEmail(to, product, expiry);
        return "✅ Test email sent!";
    }
}
