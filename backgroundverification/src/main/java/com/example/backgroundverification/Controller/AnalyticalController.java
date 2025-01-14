package com.example.backgroundverification.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.backgroundverification.service.AnalyticalService;
import com.example.backgroundverification.model.Report;

import java.util.List;

@RestController
@RequestMapping("/api/analytical")
public class AnalyticalController {

    @Autowired
    private AnalyticalService analyticalService;

    @PostMapping("/upload")
    public ResponseEntity<Report> uploadReport(@RequestParam("file") MultipartFile file,
                    @RequestParam("name") String name,
                    @RequestParam("type") String type) {
                try {
                    Report report = analyticalService.uploadReport(file, name, type);
                    return ResponseEntity.ok(report);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getReports() {
        List<Report> reports = analyticalService.getReports();
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/send-to-module/{reportId}")
    public ResponseEntity<String> sendReportToModule(@PathVariable Long reportId) {
        try {
            analyticalService.sendReportToModule(reportId);
            return ResponseEntity.ok("Report sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}

