package com.example.backgroundverification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.backgroundverification.model.Hr;
import com.example.backgroundverification.repository.HrRepository;

@Service
public class HrService {
    @Autowired
    private HrRepository hrRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Hr registerHr(Hr hr) {
        hr.setPassword(passwordEncoder.encode(hr.getPassword()));
        return hrRepository.save(hr);
    }

    public Hr findByEmail(String email) {
        return hrRepository.findByEmail(email);
    }
}

