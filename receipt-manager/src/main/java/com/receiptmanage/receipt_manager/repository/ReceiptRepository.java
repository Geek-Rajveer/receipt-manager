package com.receiptmanage.receipt_manager.repository;

import com.receiptmanage.receipt_manager.model.Receipt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceiptRepository extends MongoRepository<Receipt, String> {

    // Fetch all receipts where expiryDate is between 'start' and 'end'
    List<Receipt> findByExpiryDateBetween(LocalDate start, LocalDate end);
    List<Receipt> findByEmail(String email);
}
