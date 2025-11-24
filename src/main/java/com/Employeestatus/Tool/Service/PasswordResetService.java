package com.Employeestatus.Tool.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.Employeestatus.Tool.Model.User;
import com.Employeestatus.Tool.Model.PasswordResetToken;
import com.Employeestatus.Tool.Repository.UserRepository;
import com.Employeestatus.Tool.Repository.PasswordResetTokenRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.frontend.base-url:http://127.0.0.1:5500}")
    private String frontendBaseUrl;

    private final int TOKEN_VALID_MINUTES = 60;

    private String generateOtp() {
        Random rnd = new Random();
        int num = rnd.nextInt(900_000) + 100_000; // 100000..999999
        return String.valueOf(num);
    }

    public boolean createAndSendToken(String email) {
        if (email == null) return false;

        String normalized = email.trim().toLowerCase();
        Optional<User> optionalUser = userRepository.findFirstByEmailIgnoreCase(normalized);
        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        String otp = generateOtp();
        PasswordResetToken token = new PasswordResetToken(user, TOKEN_VALID_MINUTES, otp);
        tokenRepository.save(token);

        String link = frontendBaseUrl + "/reset-password.html?token=" + token.getToken();

        String subject = "Enabler Tool — Password Reset (OTP included)";
        String body = "Hello " + (user.getFullname() != null ? user.getFullname() : user.getUsername())
                + ",\n\nA password reset was requested for your account.\n\n"
                + "Click the link below to open the reset page:\n" + link + "\n\n"
                + "Your OTP to verify the reset is: " + otp + "\n\n"
                + "This link and OTP are valid for " + TOKEN_VALID_MINUTES + " minutes.\n\n"
                + "If you did not request this, ignore this message.";

        try {
            emailService.sendSimpleMessage(user.getEmail(), subject, body);
            System.out.println("[PasswordReset] Email sent to: " + user.getEmail() + " link=" + link + " otp=" + otp);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean verifyOtp(String token, String otp) {
        if (token == null || otp == null) return false;
        return tokenRepository.findById(token)
                .filter(t -> t.getExpiryDate().isAfter(LocalDateTime.now()))
                .filter(t -> otp.equals(t.getOtp()))
                .isPresent();
    }

    public boolean resetPassword(String token, String otp, String newPassword) {
        return tokenRepository.findById(token).map(t -> {

            if (t.getExpiryDate().isBefore(LocalDateTime.now())) {
                tokenRepository.delete(t);
                return false;
            }

            if (!otp.equals(t.getOtp())) {
                return false;
            }

            User user = t.getUser();
            String encoded = passwordEncoder.encode(newPassword);
            user.setPassword(encoded);
            userRepository.save(user);
            tokenRepository.delete(t);

            System.out.println("[PasswordReset] Password updated for user id: " + user.getId());
            return true;

        }).orElse(false);
    }
}
