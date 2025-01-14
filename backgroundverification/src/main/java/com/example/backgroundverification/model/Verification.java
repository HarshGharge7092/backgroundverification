package com.example.backgroundverification.model;

import com.example.backgroundverification.model.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "verifications")
public class Verification {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private User candidate;

    private String aadharStatus;
    private String panStatus;
    private String epfoStatus;
    private String addressStatus;
    private String educationStatus;
    private String employmentStatus;
    private String criminalRecordStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setAadharStatus(String aadharStatus) {
        this.aadharStatus = aadharStatus;
    }

    public String getAadharStatus() {
        return aadharStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Getters and setters
}

