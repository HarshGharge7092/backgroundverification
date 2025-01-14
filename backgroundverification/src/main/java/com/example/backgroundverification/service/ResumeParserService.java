package com.example.backgroundverification.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.backgroundverification.model.CandidateInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeParserService {

    public CandidateInfo parseResume(MultipartFile file) throws IOException {
        String fileContent = extractTextFromFile(file);
        return extractCandidateInfo(fileContent);
    }

    public CandidateInfo parseExternalResume(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString().toLowerCase();
        String fileContent;

        if (fileName.endsWith(".pdf")) {
            fileContent = extractTextFromPdf(Files.newInputStream(path));
        } else if (fileName.endsWith(".docx")) {
            fileContent = extractTextFromDocx(Files.newInputStream(path));
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only PDF and DOCX files are supported.");
        }

        return extractCandidateInfo(fileContent);
    }

    private String extractTextFromFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().toLowerCase();
        InputStream inputStream = file.getInputStream();

        if (fileName.endsWith(".pdf")) {
            return extractTextFromPdf(inputStream);
        } else if (fileName.endsWith(".docx")) {
            return extractTextFromDocx(inputStream);
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only PDF and DOCX files are supported.");
        }
    }

    String extractTextFromPdf(InputStream inputStream) throws IOException {
        try (PDDocument document = Loader.loadPDF((RandomAccessRead) inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    String extractTextFromDocx(InputStream inputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            return extractor.getText();
        }
    }

    private CandidateInfo extractCandidateInfo(String content) {
        CandidateInfo info = new CandidateInfo();

        // Extract name (assuming the name is at the beginning of the resume)
        Pattern namePattern = Pattern.compile("^([A-Z][a-z]+ [A-Z][a-z]+)");
        Matcher nameMatcher = namePattern.matcher(content);
        if (nameMatcher.find()) {
            String fullName = nameMatcher.group(1);
            String[] nameParts = fullName.split(" ");
            info.setFirstName(nameParts[0]);
            info.setLastName(nameParts[1]);
        }

        // Extract email
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher emailMatcher = emailPattern.matcher(content);
        if (emailMatcher.find()) {
            info.setEmail(emailMatcher.group());
        }

        // Extract phone number (assuming US format)
        Pattern phonePattern = Pattern.compile("\\b\\d{3}[-.]?\\d{3}[-.]?\\d{4}\\b");
        Matcher phoneMatcher = phonePattern.matcher(content);
        if (phoneMatcher.find()) {
            info.setPhoneNumber(phoneMatcher.group());
        }

        return info;
    }
}

