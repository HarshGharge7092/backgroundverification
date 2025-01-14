package com.example.backgroundverification.service;

import com.example.backgroundverification.model.AadharVerificationResponse;
import com.example.backgroundverification.model.EPFOVerificationResponse;
import com.example.backgroundverification.model.PANVerificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalVerificationService {

    @Autowired
    private RestTemplate restTemplate;

    public boolean verifyAadhar(String aadharNumber) {
        // Make API call to Aadhar verification service
        String url = "https://api.aadhaarverification.com/verify?number=" + aadharNumber;
        AadharVerificationResponse response = restTemplate.getForObject(url, AadharVerificationResponse.class);
        return response != null && response.isValid();
    }

    public boolean verifyPAN(String panNumber) {
        // Make API call to PAN verification service
        String url = "https://api.panverification.com/verify?number=" + panNumber;
        PANVerificationResponse response = restTemplate.getForObject(url, PANVerificationResponse.class);
        return response != null && response.isValid();
    }

    public boolean verifyEPFO(String epfoNumber) {
        // Make API call to EPFO verification service
        String url = "https://api.epfoverification.com/verify?number=" + epfoNumber;
        EPFOVerificationResponse response = restTemplate.getForObject(url, EPFOVerificationResponse.class);
        return response != null && response.isValid();
    }
}

