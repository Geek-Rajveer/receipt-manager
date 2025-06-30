package com.receiptmanage.receipt_manager.service;

import com.receiptmanage.receipt_manager.model.Receipt;

import java.util.List;
import java.util.Optional;

public interface ReceiptService {
    Receipt addReceipt(Receipt receipt);
    Optional<Receipt> getReceiptById(String id);
    Receipt updateReceipt(String id, Receipt updatedReceipt);
    void deleteReceipt(String id);

    // ✅ Only needed method now
    List<Receipt> getReceiptsByEmail(String email);
}
