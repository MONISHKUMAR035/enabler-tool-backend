package com.Employeestatus.Tool.Controller;

import com.Employeestatus.Tool.Service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password-reset")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500", "http://localhost:8080"})
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<String> requestReset(@RequestBody EmailRequest req) {
        if (req == null || req.getEmail() == null || req.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        boolean sent = passwordResetService.createAndSendToken(req.getEmail());
        return sent ? ResponseEntity.ok("Email sent") :
                      ResponseEntity.badRequest().body("Email not found");
    }

    // Verify OTP only (token + otp)
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyRequest req) {
        if (req == null || req.getToken() == null || req.getOtp() == null) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
        boolean ok = passwordResetService.verifyOtp(req.getToken(), req.getOtp());
        return ok ? ResponseEntity.ok("OTP verified") :
                    ResponseEntity.badRequest().body("Invalid or expired OTP");
    }

    // Confirm reset with token + otp + newPassword
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmReset(@RequestBody ConfirmRequest req) {
        if (req == null || req.getToken() == null || req.getOtp() == null || req.getNewPassword() == null) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
        boolean success = passwordResetService.resetPassword(req.getToken(), req.getOtp(), req.getNewPassword());
        return success ? ResponseEntity.ok("Password updated") :
                         ResponseEntity.badRequest().body("Invalid or expired token/OTP");
    }

    public static class EmailRequest {
        private String email;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class VerifyRequest {
        private String token;
        private String otp;
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getOtp() { return otp; }
        public void setOtp(String otp) { this.otp = otp; }
    }

    public static class ConfirmRequest {
        private String token;
        private String otp;
        private String newPassword;
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getOtp() { return otp; }
        public void setOtp(String otp) { this.otp = otp; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}
