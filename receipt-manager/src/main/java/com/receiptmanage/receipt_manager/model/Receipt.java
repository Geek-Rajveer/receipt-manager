package com.receiptmanage.receipt_manager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "receipts")
public class Receipt {

    @Id
    private String id;

    private String productName;
    private String storeName;
    private LocalDate purchaseDate;
    private int warrantyMonths;
    private String notes;
    private String fileUrl;
    private LocalDate expiryDate;

    private String email; // ✅ renamed from userEmail ➝ email

    public Receipt() {
    }

    public Receipt(String productName, String storeName, LocalDate purchaseDate, int warrantyMonths, String notes, String fileUrl, String email) {
        this.productName = productName;
        this.storeName = storeName;
        this.purchaseDate = purchaseDate;
        this.warrantyMonths = warrantyMonths;
        this.notes = notes;
        this.fileUrl = fileUrl;
        this.expiryDate = purchaseDate.plusMonths(warrantyMonths);
        this.email = email;
    }

    // --- Getters & Setters ---

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public String getNotes() {
        return notes;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getEmail() {  // ✅ updated
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setEmail(String email) {  // ✅ updated
        this.email = email;
    }
}
