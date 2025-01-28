package com.CatPhish.phishing_web_app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PhishingDetectionService {

    @Autowired
    private RestTemplate restTemplate;

    // API key and base URL for the Bolster Scan API
    private static final String API_KEY = "so4whvz8bxmbssjqwnfzxbugnmrpww2dfvm54l4vo8dm35583xvle7dwxys5s739";
    private static final String BASE_URL = "https://developers.bolster.ai/api/neo/scan";

    public boolean isPhishing(String url) {
        // Prepare the request payload for a quick scan
        String jsonPayload = String.format("{\"apiKey\": \"%s\", \"urlInfo\": {\"url\": \"%s\"}, \"scanType\": \"quick\"}", API_KEY, url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        try {
            // Sending POST request to Bolster API
            ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.POST, request, String.class);
            String response = responseEntity.getBody();

            // If HTML response detected, log and return false
            if (response != null && response.trim().startsWith("<")) {
                System.err.println("Received HTML response, possible error: " + response);
                return false;
            }

            // Extract jobID from the response
            String jobId = extractJobId(response);
            if (jobId != null) {
                return getScanResult(jobId);
            } else {
                System.err.println("Job ID extraction failed.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error while checking phishing for URL: " + url);
            e.printStackTrace();
            return false;
        }
    }

    private String extractJobId(String response) {
        // Logic to extract jobID from the JSON response
        if (response.contains("jobID")) {
            String jobId = response.substring(response.indexOf("jobID\":\"") + 8);
            jobId = jobId.substring(0, jobId.indexOf("\""));
            return jobId;
        }
        return null;
    }

    private boolean getScanResult(String jobId) {
        // Prepare the request to check scan result
        String statusUrl = "https://developers.bolster.ai/api/neo/scan/status";
        String statusPayload = String.format("{\"apiKey\": \"%s\", \"jobID\": \"%s\", \"insights\": true}", API_KEY, jobId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(statusPayload, headers);

        try {
            // Sending POST request to get the scan status
            ResponseEntity<String> responseEntity = restTemplate.exchange(statusUrl, HttpMethod.POST, request, String.class);
            String response = responseEntity.getBody();

            // If the status is "DONE" and the disposition is "phish", return true
            if (response != null && response.contains("\"status\":\"DONE\"") && response.contains("\"disposition\":\"phish\"")) {
                System.out.println("Phishing detected for URL: " + jobId);
                return true;
            } else {
                System.out.println("Scan result for URL: " + jobId + " - Not phishing");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error while checking scan result for jobID: " + jobId);
            e.printStackTrace();
            return false;
        }
    }
}
