package com.example.backgroundverification.dto;

import java.time.LocalDateTime;

public class ReportDTO {
    private String reportType;
    private String extractedText;
    private LocalDateTime extractionDate;
    private String fileName;
    private String fileFormat;
    private String summary;

    // Getters and Setters
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public LocalDateTime getExtractionDate() {
        return extractionDate;
    }

    public void setExtractionDate(LocalDateTime extractionDate) {
        this.extractionDate = extractionDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

