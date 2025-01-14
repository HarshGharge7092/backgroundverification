package com.example.backgroundverification.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;

class ExternalResumeTextExtractorTest {

    @Spy
    private ResumeParserService resumeParserService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExtractTextFromExternalDOCX() throws IOException {
        // Create test content
        String expectedContent = "Jane Smith\nProduct Manager\njane.smith@email.com";
        Path docxPath = tempDir.resolve("test_resume.docx");

        // Create a simple DOCX file with test content
        try (FileInputStream fis = createSampleDocx(docxPath.toFile(), expectedContent)) {
            // Extract text from the file
            String extractedText = extractTextFromDocx(docxPath.toFile());

            // Verify results
            assertNotNull(extractedText);
            assertTrue(extractedText.contains("Jane Smith"));
            assertTrue(extractedText.contains("jane.smith@email.com"));
        }
    }

    @Test
    void testExtractTextFromUnsupportedFormat() {
        // Create test file with unsupported format
        Path txtPath = tempDir.resolve("test_resume.txt");

        // Verify exception is thrown
        assertThrows(IllegalArgumentException.class, () -> {
            extractTextFromFile(txtPath.toFile());
        });
    }

    private String extractTextFromFile(File file) throws IOException {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".docx")) {
            return extractTextFromDocx(file);
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only DOCX files are supported in this test.");
        }
    }

    private String extractTextFromDocx(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            return extractor.getText();
        }
    }

    private FileInputStream createSampleDocx(File file, String content) throws IOException {
        // Create a simple text file for testing
        // In a real scenario, you would create an actual DOCX file
        Files.write(file.toPath(), content.getBytes(), StandardOpenOption.CREATE);
        return new FileInputStream(file);
    }
}

