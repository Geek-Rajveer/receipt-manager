package com.receiptmanage.receipt_manager.controller;

import com.receiptmanage.receipt_manager.model.Receipt;
import com.receiptmanage.receipt_manager.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/receipts")
@CrossOrigin(origins = "*")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping
    public Receipt addReceipt(@RequestBody Receipt receipt) {
        return receiptService.addReceipt(receipt);
    }

    // ✅ Only return receipts for a specific email (e.g., ?email=user@gmail.com)
    @GetMapping
    public List<Receipt> getReceiptsByUser(@RequestParam String email) {
        return receiptService.getReceiptsByEmail(email);
    }

    @GetMapping("/{id}")
    public Optional<Receipt> getReceiptById(@PathVariable String id) {
        return receiptService.getReceiptById(id);
    }

    @PutMapping("/{id}")
    public Receipt updateReceipt(@PathVariable String id, @RequestBody Receipt receipt) {
        return receiptService.updateReceipt(id, receipt);
    }

    @DeleteMapping("/{id}")
    public void deleteReceipt(@PathVariable String id) {
        receiptService.deleteReceipt(id);
    }
}
