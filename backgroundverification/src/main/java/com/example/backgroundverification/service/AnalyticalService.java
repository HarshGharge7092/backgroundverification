package com.example.backgroundverification.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.backgroundverification.model.Report;
import com.example.backgroundverification.repository.ReportRepository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnalyticalService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Report uploadReport(MultipartFile file, String name, String type) throws IOException {
        Report report = new Report();
        report.setName(name);
        report.setType(type);
        report.setUploadDate(LocalDateTime.now());
        report.setFileSize(file.getSize());

        // Extract text from PDF
        String extractedText = extractTextFromPdf(file);
        report.setExtractedText(extractedText);

        return reportRepository.save(report);
    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (PDDocument document = Loader.loadPDF((RandomAccessRead) file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public List<Report> getReports() {
        return reportRepository.findAll();
    }

    public void sendReportToModule(Long reportId) throws Exception {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new Exception("Report not found"));

        // Send report data to another module via API
        String apiUrl = "http://another-module-api/receive-report";
        restTemplate.postForObject(apiUrl, report, String.class);
    }
}

