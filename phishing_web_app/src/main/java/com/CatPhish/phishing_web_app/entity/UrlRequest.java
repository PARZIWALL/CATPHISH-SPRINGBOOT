package com.CatPhish.phishing_web_app.entity;


import lombok.Data;

@Data
public class UrlRequest {
    private String url;
    public String getUrl() {
        return this.url;
    }

}
