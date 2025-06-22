package com.rgz.short_url_backend.controller;

import com.rgz.short_url_backend.dto.UrlRequest;
import com.rgz.short_url_backend.dto.UrlResponse;
import com.rgz.short_url_backend.entity.Url;
import com.rgz.short_url_backend.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
@RestController
@RequestMapping("/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@Valid @RequestBody UrlRequest request) {
        String shortUrl = urlService.shortenUrl(request.getUrl());
        return ResponseEntity.ok(new UrlResponse("http://rgz.com/" + shortUrl));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirectToOriginal(@PathVariable String shortUrl) {
        Url url = urlService.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL não encontrada"));

        if (url.getExpiration().isBefore(LocalDateTime.now())) {
            urlService.deleteUrl(url);
            return ResponseEntity.status(HttpStatus.GONE).body("URL expirada");
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", url.getOriginalUrl())
                .build();
    }

}
