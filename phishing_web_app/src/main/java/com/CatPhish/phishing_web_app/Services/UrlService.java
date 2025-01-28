package com.CatPhish.phishing_web_app.Services;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UrlService {

    // Method to validate the URL format
    public boolean isValidURL(String url) {
        try {
            URI uri = new URI(url);
            // Check if the URL is a valid URI and if it contains the necessary parts like scheme and host
            return uri.isAbsolute() && uri.getHost() != null && uri.getScheme() != null;
        } catch (URISyntaxException e) {
            return false;  // Invalid URL format
        }
    }
}
