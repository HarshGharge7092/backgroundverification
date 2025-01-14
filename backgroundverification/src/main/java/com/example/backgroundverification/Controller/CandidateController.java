package com.example.backgroundverification.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.backgroundverification.model.CandidateInfo;
import com.example.backgroundverification.service.ResumeParserService;

import java.io.IOException;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private ResumeParserService resumeParserService;

    @PostMapping("/parse-resume")
    public ResponseEntity<CandidateInfo> parseResume(@RequestParam("resume") MultipartFile file) {
        try {
            CandidateInfo candidateInfo = resumeParserService.parseResume(file);
            return ResponseEntity.ok(candidateInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/parse-external-resume")
    public ResponseEntity<CandidateInfo> parseExternalResume(@RequestParam("filePath") String filePath) {
        try {
            CandidateInfo candidateInfo = resumeParserService.parseExternalResume(filePath);
            return ResponseEntity.ok(candidateInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

