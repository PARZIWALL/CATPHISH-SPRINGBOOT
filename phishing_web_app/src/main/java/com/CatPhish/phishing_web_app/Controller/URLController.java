package com.CatPhish.phishing_web_app.Controller;


import com.CatPhish.phishing_web_app.Services.Alexa1MService;
import com.CatPhish.phishing_web_app.Services.MLModelService;
import com.CatPhish.phishing_web_app.Services.PhishingDetectionService;
import com.CatPhish.phishing_web_app.Services.UrlService;
import com.CatPhish.phishing_web_app.entity.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkURL")
public class URLController {

    @Autowired
    private Alexa1MService alexa1MService;

    @Autowired
    private PhishingDetectionService phishingDetectionService;

    @Autowired
    private UrlService urlValidationService;

    @Autowired
    private MLModelService mlModelService;

    @PostMapping
    public ResponseEntity<?> checkURL(@RequestBody UrlRequest urlRequest) {
        String url = urlRequest.getUrl();
        if (url == null || url.isEmpty()) {
            return ResponseEntity.badRequest().body("URL was empty or null");
        }

        int checkSum = 0;  // Variable to accumulate risky checks

        // 1. Validate URL format first (invalid format increases risk)
        // 1. Validate URL format first (invalid format increases risk)
        if (!urlValidationService.isValidURL(url)) {
            checkSum++;  // Invalid format adds to the risk
        }

        // 2. Check if URL is part of the Alexa Top 1 Million
        if (!alexa1MService.findDomain(url)) {
            checkSum++;  // If it's not in the Alexa 1M list, it adds to the risk
        }

        // 3. Check for phishing using Phishing detection service (if detected, increases risk)
        if (phishingDetectionService.isPhishing(url)) {
            checkSum++;  // Phishing detection adds to the risk
        }
        // 4. Check using ML model for malicious prediction (if detected, increases risk)
//        if (mlModelService.predictWithCache(url)) {
//            checkSum++;  // ML model prediction adds to the risk
//        }

        // 5. Final decision based on checkSum threshold
//        if (checkSum >= 3) {
//            // If 3 or more checks indicate an issue, the URL is risky
//            return new ResponseEntity<>("The URL is risky, sum of issues: " + checkSum, HttpStatus.BAD_REQUEST);
//        }

        // If checkSum is less than or equal to 2, the URL is considered safe
        return new ResponseEntity<>("The URL is safe, sum of issues: " + checkSum, HttpStatus.OK);
    }
}
