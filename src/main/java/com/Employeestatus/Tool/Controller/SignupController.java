package com.Employeestatus.Tool.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Employeestatus.Tool.Model.User;
import com.Employeestatus.Tool.Repository.UserRepository;
import com.Employeestatus.Tool.Service.ExcelService;
import com.Employeestatus.Tool.Service.EmailService; // ✅ Added import

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private EmailService emailService; // ✅ Added

    @PostMapping("/signup")
    public String signupUser(@RequestBody User user) {
        userRepository.save(user); // ✅ Save user to DB
        excelService.writeUserToExcel(user); // ✅ Write to Excel

        // ✅ Send confirmation email
        try {
            emailService.sendRegistrationEmail(user.getEmail(), user.getUsername());
        } catch (Exception e) {
            System.out.println("❌ Error sending email: " + e.getMessage());
        }

        return "✅ User registered successfully! A confirmation email has been sent.";
    }
}
