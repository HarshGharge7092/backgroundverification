package com.example.backgroundverification.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backgroundverification.model.Hr;
import com.example.backgroundverification.service.HrService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private HrService hrService;

    @PostMapping("/register-hr")
    public ResponseEntity<?> registerHr(@RequestBody Hr hr) {
        if (hrService.findByEmail(hr.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }
        Hr registeredHr = hrService.registerHr(hr);
        return ResponseEntity.ok(registeredHr);
    }

    // Other authentication methods (login, logout, etc.)
}

