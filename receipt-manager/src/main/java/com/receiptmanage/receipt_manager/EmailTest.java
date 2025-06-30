package com.receiptmanage.receipt_manager;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailTest {
    public static void main(String[] args) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("rajveersengar03@gmail.com"); // <- your Gmail ID
        mailSender.setPassword("rzfbalndbhgaityp"); // <- your Gmail App Password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("rajveersengar03@gmail.com"); // <- can be same or different
        message.setSubject("Test Email");
        message.setText("✅ This is a test email from Spring Boot.");

        try {
            mailSender.send(message);
            System.out.println("✅ Email sent successfully!");
        } catch (Exception e) {
            System.out.println("❌ Failed to send email:");
            e.printStackTrace();
        }
    }
}
