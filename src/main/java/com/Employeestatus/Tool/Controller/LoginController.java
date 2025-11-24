package com.Employeestatus.Tool.Controller;

import com.Employeestatus.Tool.Model.User;
import com.Employeestatus.Tool.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        if (loginRequest == null
            || loginRequest.getLoginValue() == null || loginRequest.getLoginValue().trim().isEmpty()
            || loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("username/email and password are required");
        }

        String loginValueRaw = loginRequest.getLoginValue().trim();
        String password = loginRequest.getPassword();

        Optional<User> userOptional = userRepository.findFirstByUsername(loginValueRaw);

        if (userOptional.isEmpty()) {
            userOptional = userRepository.findFirstByEmailIgnoreCase(loginValueRaw.toLowerCase());
        }

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("User not found");
        }

        User user = userOptional.get();

        if (user.getPassword() == null) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        String stored = user.getPassword();

        // simple bcrypt detection:
        boolean looksLikeBcrypt = stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$");

        if (looksLikeBcrypt) {
            if (passwordEncoder.matches(password, stored)) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        } else {
            // stored password appears to be plaintext -> compare directly
            if (stored.equals(password)) {
                // migrate: encode and save
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                return ResponseEntity.ok("Login successful (password migrated)");
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        }
    }

    public static class LoginRequest {
        private String loginValue;
        private String password;
        public String getLoginValue() { return loginValue; }
        public void setLoginValue(String loginValue) { this.loginValue = loginValue; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
