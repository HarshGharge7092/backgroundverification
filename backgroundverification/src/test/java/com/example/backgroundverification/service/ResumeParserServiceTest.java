package com.example.backgroundverification.service;

import com.example.backgroundverification.model.CandidateInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResumeParserServiceTest {

    @Spy
    @InjectMocks
    private ResumeParserService resumeParserService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParseResumePDF() throws IOException {
        // Prepare test data
        String pdfContent = "John Doe\njohndoe@email.com\n123-456-7890\n";
        MockMultipartFile file = new MockMultipartFile("resume.pdf", "resume.pdf", "application/pdf", pdfContent.getBytes());

        // Mock behavior
        doReturn(pdfContent).when(resumeParserService).extractTextFromPdf(any());

        // Execute the method
        CandidateInfo result = resumeParserService.parseResume(file);

        // Verify the results
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("johndoe@email.com", result.getEmail());
        assertEquals("123-456-7890", result.getPhoneNumber());

        // Verify that the correct method was called
        verify(resumeParserService).extractTextFromPdf(any());
    }

    @Test
    void testParseResumeDocx() throws IOException {
        // Prepare test data
        String docxContent = "Jane Smith\njanesmith@email.com\n987-654-3210\n";
        MockMultipartFile file = new MockMultipartFile("resume.docx", "resume.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", docxContent.getBytes());

        // Mock behavior
        doReturn(docxContent).when(resumeParserService).extractTextFromDocx(any());

        // Execute the method
        CandidateInfo result = resumeParserService.parseResume(file);

        // Verify the results
        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("janesmith@email.com", result.getEmail());
        assertEquals("987-654-3210", result.getPhoneNumber());

        // Verify that the correct method was called
        verify(resumeParserService).extractTextFromDocx(any());
    }

    @Test
    void testParseResumeUnsupportedFormat() {
        // Prepare test data
        MockMultipartFile file = new MockMultipartFile("resume.txt", "resume.txt", "text/plain", "Some content".getBytes());

        // Execute and verify
        assertThrows(IllegalArgumentException.class, () -> resumeParserService.parseResume(file));
    }

    @Test
    void testParseExternalResumePDF() throws IOException {
        // Prepare test data
        String pdfContent = "Alice Johnson\nalicejohnson@email.com\n555-123-4567\n";
        Path pdfPath = tempDir.resolve("external_resume.pdf");
        Files.write(pdfPath, pdfContent.getBytes());

        // Mock behavior
        doReturn(pdfContent).when(resumeParserService).extractTextFromPdf(any());

        // Execute the method
        CandidateInfo result = resumeParserService.parseExternalResume(pdfPath.toString());

        // Verify the results
        assertNotNull(result);
        assertEquals("Alice", result.getFirstName());
        assertEquals("Johnson", result.getLastName());
        assertEquals("alicejohnson@email.com", result.getEmail());
        assertEquals("555-123-4567", result.getPhoneNumber());

        // Verify that the correct method was called
        verify(resumeParserService).extractTextFromPdf(any());
    }

    @Test
    void testParseExternalResumeDocx() throws IOException {
        // Prepare test data
        String docxContent = "Bob Brown\nbobbrown@email.com\n777-888-9999\n";
        Path docxPath = tempDir.resolve("external_resume.docx");
        Files.write(docxPath, docxContent.getBytes());

        // Mock behavior
        doReturn(docxContent).when(resumeParserService).extractTextFromDocx(any());

        // Execute the method
        CandidateInfo result = resumeParserService.parseExternalResume(docxPath.toString());

        // Verify the results
        assertNotNull(result);
        assertEquals("Bob", result.getFirstName());
        assertEquals("Brown", result.getLastName());
        assertEquals("bobbrown@email.com", result.getEmail());
        assertEquals("777-888-9999", result.getPhoneNumber());

        // Verify that the correct method was called
        verify(resumeParserService).extractTextFromDocx(any());
    }

    @Test
    void testParseExternalResumeUnsupportedFormat() {
        // Prepare test data
        Path txtPath = tempDir.resolve("external_resume.txt");

        // Execute and verify
        assertThrows(IllegalArgumentException.class, () -> resumeParserService.parseExternalResume(txtPath.toString()));
    }
}

