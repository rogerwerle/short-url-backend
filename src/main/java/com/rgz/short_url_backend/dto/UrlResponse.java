package com.rgz.short_url_backend.dto;

public class UrlResponse {
    private String url;

    public UrlResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}