package com.Employeestatus.Tool.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Existing registration mail
    public void sendRegistrationEmail(String toEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to Enabler Tool!");
        message.setText("Hello " + username + ",\n\n" +
                "You have successfully registered on the Enabler Tool platform.\n\n" +
                "Thank you for joining us!\n\n" +
                "Best Regards,\nEnabler Tool Team");
        mailSender.send(message);
    }

    // ✅ Add this missing method so PasswordResetService works
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
