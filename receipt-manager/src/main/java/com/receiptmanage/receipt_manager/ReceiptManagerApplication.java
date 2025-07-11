package com.receiptmanage.receipt_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReceiptManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiptManagerApplication.class, args);
	}
}
