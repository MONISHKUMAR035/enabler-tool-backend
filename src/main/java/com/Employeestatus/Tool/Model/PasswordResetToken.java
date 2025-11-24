package com.Employeestatus.Tool.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    @Column(length = 64)
    private String token; // UUID string

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_password_reset_tokens_user"))
    private User user;

    private LocalDateTime expiryDate;

    @Column(length = 8)
    private String otp; // e.g. "123456"

    public PasswordResetToken() {}

    public PasswordResetToken(User user, int minutesValid, String otp) {
        this.token = UUID.randomUUID().toString();
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusMinutes(minutesValid);
        this.otp = otp;
    }

    public String getToken() { return token; }
    public User getUser() { return user; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
}
