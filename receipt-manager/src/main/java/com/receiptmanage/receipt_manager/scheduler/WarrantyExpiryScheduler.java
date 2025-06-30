package com.receiptmanage.receipt_manager.scheduler;

import com.receiptmanage.receipt_manager.model.Receipt;
import com.receiptmanage.receipt_manager.repository.ReceiptRepository;
import com.receiptmanage.receipt_manager.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class WarrantyExpiryScheduler {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private EmailService emailService;

    // Runs every day at 9 AM
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkExpiringWarranties() {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.plusDays(7); // Email alert for 7 days before expiry

        List<Receipt> allReceipts = receiptRepository.findAll();

        for (Receipt receipt : allReceipts) {
            LocalDate expiryDate = receipt.getExpiryDate();

            if (expiryDate != null && expiryDate.equals(targetDate)) {
                String to = "rajveersengar03@gmail.com"; // Replace with your actual email
                String productName = receipt.getProductName();
                String expiry = expiryDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

                emailService.sendWarrantyExpiryEmail(to, productName, expiry);
                System.out.println("📬 Sent alert for: " + productName);
            }
        }

        System.out.println("✅ Warranty check ran on: " + today);
    }
}
