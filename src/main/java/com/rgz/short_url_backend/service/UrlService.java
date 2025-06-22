package com.rgz.short_url_backend.service;

import com.rgz.short_url_backend.entity.Url;
import com.rgz.short_url_backend.repository.UrlRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl) {
        String shortUrl = generateUniqueShortUrl();
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setExpiration(LocalDateTime.now().plusDays(1));
        urlRepository.save(url);
        return shortUrl;
    }

    public Optional<Url> findByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }

    public String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 5 + random.nextInt(6);
        for(int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public String generateUniqueShortUrl(){
        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (urlRepository.existsByShortUrl(shortUrl));
        return shortUrl;
    }

    public void deleteUrl(Url url) {
        urlRepository.delete(url);
    }


    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void deleteExpiredUrls() {
        int deletedCount = urlRepository.deleteByExpirationBefore(LocalDateTime.now());
        System.out.println("URLs expiradas removidas: " + deletedCount);
    }
}
