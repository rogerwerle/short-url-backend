package com.rgz.short_url_backend.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class UrlRequest {

    @NotBlank(message = "A URL não pode estar vazia.")
    @URL(message = "Formato de URL inválido.")
    private String url;

    // Getters e Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
