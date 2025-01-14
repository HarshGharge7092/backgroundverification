package com.example.backgroundverification.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.backgroundverification.dto.ReportDTO;
import com.example.backgroundverification.service.DocumentTextExtractorService;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private DocumentTextExtractorService documentTextExtractorService;

    @PostMapping("/extract/{reportType}")
    public ResponseEntity<ReportDTO> extractReportText(
            @PathVariable String reportType,
            @RequestParam("file") MultipartFile file) {
        try {
            ReportDTO reportDTO = documentTextExtractorService.extractText(file, reportType);
            return ResponseEntity.ok(reportDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}

