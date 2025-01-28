package com.CatPhish.phishing_web_app.Services;


import com.CatPhish.phishing_web_app.Repository.AlexaSiteRepository;
import com.CatPhish.phishing_web_app.entity.AlexaSite;
import com.CatPhish.phishing_web_app.entity.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Alexa1MService {


    @Autowired
    private AlexaSiteRepository alexaSiteRepository;

    // Fetch domain details by domain name
    public Boolean findDomain(String urlRaw) {

        // Define the regex to extract the domain name
        String regex = "^(?:https?:\\/\\/)?(?:www\\.)?([^:\\/\\n?]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(urlRaw);

        // If the URL matches the pattern, extract the domain
        if (matcher.find()) {
            String mainUrl = matcher.group(1);  // This is the domain name (without 'www' or protocol)

            // Check if the domain exists in the database
            if (alexaSiteRepository.findByDomainName(mainUrl) != null) {
                return true;  // Domain found in the database
            }
        }

        // If the domain does not match the regex or isn't found in the database, return false
        return false;
    }
    // Method to quickly check if the domain is in Alexa Top 1 Million

}
