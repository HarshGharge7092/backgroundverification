package com.example.backgroundverification.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backgroundverification.model.*;
import com.example.backgroundverification.service.VerificationService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/request-check")
    public ResponseEntity<String> requestCheck(@RequestBody UserData userData) {
        try {
            String result = verificationService.requestCheck(userData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error requesting check: " + e.getMessage());
        }
    }

    @GetMapping("/candidates")
    public ResponseEntity<List<Candidate>> getCandidates() {
        try {
            List<Candidate> candidateList = verificationService.getCandidates();
            return ResponseEntity.ok(candidateList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/extract-text")
    public ResponseEntity<String> extractText(@RequestParam("file") MultipartFile file) {
        try {
            String extractedText = verificationService.extractText(file);
            return ResponseEntity.ok(extractedText);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error extracting text: " + e.getMessage());
        }
    }

    @PostMapping("/generate-report")
    public ResponseEntity<String> generateReport(@RequestBody ReportData reportData) {
        try {
            String generatedReport = verificationService.generateReport(reportData);
            return ResponseEntity.ok(generatedReport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating report: " + e.getMessage());
        }
    }
}

