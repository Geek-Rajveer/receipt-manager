package com.receiptmanage.receipt_manager.service;

import com.receiptmanage.receipt_manager.model.Receipt;
import com.receiptmanage.receipt_manager.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private EmailService emailService;  // Inject EmailService

    @Override
    public Receipt addReceipt(Receipt receipt) {
        Receipt savedReceipt = receiptRepository.save(receipt);

        // ✅ Log the saved receipt
        System.out.println("✅ Saved receipt: " + savedReceipt);

        if (receipt.getPurchaseDate() != null && receipt.getWarrantyMonths() > 0) {
            LocalDate expiryDate = receipt.getPurchaseDate().plusMonths(receipt.getWarrantyMonths());

            emailService.sendWarrantyExpiryEmail(
                    receipt.getEmail(),
                    receipt.getProductName(),
                    expiryDate.toString()
            );
        }

        return savedReceipt;
    }


    @Override
    public Optional<Receipt> getReceiptById(String id) {
        return receiptRepository.findById(id);
    }

    @Override
    public Receipt updateReceipt(String id, Receipt updatedReceipt) {
        Optional<Receipt> existing = receiptRepository.findById(id);
        if (existing.isPresent()) {
            updatedReceipt.setId(id);
            return receiptRepository.save(updatedReceipt);
        }
        return null;
    }

    @Override
    public void deleteReceipt(String id) {
        receiptRepository.deleteById(id);
    }

    @Override
    public List<Receipt> getReceiptsByEmail(String email) {
        List<Receipt> receipts = receiptRepository.findByEmail(email);
        System.out.println("📦 Fetching receipts for: " + email);
        System.out.println("📄 Receipts found: " + receipts.size());
        receipts.forEach(System.out::println); // Print each receipt
        return receipts;
    }

}
