package com.example.backgroundverification.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.backgroundverification.dto.ReportDTO;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class DocumentTextExtractorService {

    @Autowired
    private SummaryGenerationService summaryGenerationService;

    public ReportDTO extractText(MultipartFile file, String reportType) throws IOException {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportType(reportType);
        reportDTO.setFileName(file.getOriginalFilename());
        reportDTO.setExtractionDate(LocalDateTime.now());
        reportDTO.setFileFormat(getFileExtension(file.getOriginalFilename()));

        String extractedText = extractTextFromFile(file);
        reportDTO.setExtractedText(extractedText);
        reportDTO.setSummary(summaryGenerationService.generateSummary(extractedText));

        return reportDTO;
    }

    private String extractTextFromFile(MultipartFile file) throws IOException {
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();
        if (fileName.endsWith(".pdf")) {
            return extractTextFromPdf(file.getInputStream());
        } else if (fileName.endsWith(".docx")) {
            return extractTextFromDocx(file.getInputStream());
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only PDF and DOCX files are supported.");
        }
    }

    private String extractTextFromPdf(InputStream inputStream) throws IOException {
        try (PDDocument document = Loader.loadPDF((RandomAccessRead) inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractTextFromDocx(InputStream inputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            return extractor.getText();
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) return "";
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}

