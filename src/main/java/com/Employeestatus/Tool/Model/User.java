package com.Employeestatus.Tool.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      // <-- changed to Long to match DB bigint

    private String username;

    private String fullname;

    @Column(unique = true)
    private String email;

    private String password;

    // getters / setters...

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null) {
            this.email = null;
        } else {
            this.email = email.trim().toLowerCase();
        }
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
