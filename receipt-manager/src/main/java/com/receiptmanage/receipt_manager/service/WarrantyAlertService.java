package com.receiptmanage.receipt_manager.service;

import com.receiptmanage.receipt_manager.model.Receipt;
import com.receiptmanage.receipt_manager.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WarrantyAlertService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private EmailService emailService;

    // 🕗 Runs every day at 8:00 AM
    @Scheduled(cron = "0 0 8 * * *")
    public void checkForExpiringWarranties() {
        LocalDate today = LocalDate.now();
        LocalDate in3Days = today.plusDays(3);

        List<Receipt> expiringReceipts = receiptRepository.findByExpiryDateBetween(today, in3Days);

        for (Receipt receipt : expiringReceipts) {
            String email = "your.email@gmail.com"; // 👈 Replace with recipient (or store in DB per user)
            String product = receipt.getProductName();
            String expiry = receipt.getExpiryDate().toString();

            emailService.sendWarrantyExpiryEmail(email, product, expiry);
            System.out.println("Email sent for: " + product);
        }
    }
}
