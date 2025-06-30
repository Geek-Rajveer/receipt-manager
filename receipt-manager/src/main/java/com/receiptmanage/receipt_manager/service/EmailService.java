package com.receiptmanage.receipt_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWarrantyExpiryEmail(String to, String productName, String expiryDate) {
        // 🛡️ Defensive check to avoid null crash
        if (to == null || to.trim().isEmpty()) {
            System.out.println("❌ Email address is null or empty. Skipping email send.");
            return;
        }

        System.out.println("📧 Trying to send email to: " + to);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("⏰ Warranty Expiry Reminder");
        message.setText("Hi,\n\nYour warranty for **" + productName + "** is expiring on " + expiryDate + ".\n\nPlease take action if needed.\n\n- Receipt Manager");

        mailSender.send(message);
    }

}
