package com.Employeestatus.Tool.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_status")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "emp_name")
    private String name;

    private String department;
    private String country;

    // keep region column
    private String region;

    private String state;
    private String status;

    @Column(name = "project_name")
    private String project_name;

    private String email;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "joining_date")
    private LocalDate joining_date;

    @Column(name = "last_updated")
    private LocalDateTime last_updated;

    public Employee() {}

    // --- lifecycle hooks to set last_updated automatically ---
    @PrePersist
    protected void onCreate() {
        this.last_updated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.last_updated = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getProject_name() { return project_name; }
    public void setProject_name(String project_name) { this.project_name = project_name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone_number() { return phone_number; }
    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }

    public LocalDate getJoining_date() { return joining_date; }
    public void setJoining_date(LocalDate joining_date) { this.joining_date = joining_date; }

    public LocalDateTime getLast_updated() { return last_updated; }
    public void setLast_updated(LocalDateTime last_updated) { this.last_updated = last_updated; }
}
