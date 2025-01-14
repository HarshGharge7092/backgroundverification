package com.example.backgroundverification.service;

import org.apache.pdfbox.io.RandomAccessRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backgroundverification.model.*;
import com.example.backgroundverification.repository.CandidateRepository;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.util.List;

@Service
public class VerificationService {

    @Autowired
    private CandidateRepository candidateRepository;

    public String requestCheck(UserData userData) {
        // Implement logic to create a new check request
        Candidate candidate = new Candidate();
        candidate.setFirstName(userData.getFirstName());
        candidate.setLastName(userData.getLastName());
        candidate.setEmail(userData.getEmail());
        candidate.setPhoneNumber(userData.getMobile());
        candidate.setVerificationStatus("Pending");
        candidateRepository.save(candidate);
        return "Check requested successfully for " + userData.getFirstName() + " " + userData.getLastName();
    }

    public List<Candidate> getCandidates() {
        return candidateRepository.findAll();
    }

    public String extractText(MultipartFile file) throws IOException {
        if (file.getOriginalFilename().endsWith(".pdf")) {
            try (PDDocument document = Loader.loadPDF((RandomAccessRead) file.getInputStream())) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        } else {
            throw new IllegalArgumentException("Unsupported file type. Only PDF files are supported.");
        }
    }

    public String generateReport(ReportData reportData) {
        // Implement logic to generate final report
        // For now, we'll just return a simple report
        return "Report Summary:\n\nExtracted Text: " + reportData.getExtractedText() +
                "\n\nGenerated Summary: " + reportData.getGeneratedSummary();
    }
}

