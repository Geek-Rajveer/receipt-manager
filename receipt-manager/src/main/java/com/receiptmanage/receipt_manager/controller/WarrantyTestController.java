package com.receiptmanage.receipt_manager.controller;

import com.receiptmanage.receipt_manager.scheduler.WarrantyExpiryScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manual-test")
@CrossOrigin(origins = "*")
public class WarrantyTestController {

    @Autowired
    private WarrantyExpiryScheduler scheduler;

    @GetMapping
    public String triggerManually() {
        scheduler.checkExpiringWarranties();
        return "✅ Manual warranty check triggered.";
    }
}
