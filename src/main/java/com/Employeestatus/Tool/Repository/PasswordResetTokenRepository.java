package com.Employeestatus.Tool.Repository;

import com.Employeestatus.Tool.Model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
    // you can add helpers later if needed
}
