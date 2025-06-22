package com.rgz.short_url_backend.repository;

import com.rgz.short_url_backend.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortUrl(String shorUrl);

    boolean existsByShortUrl(String shortUrl);

    int deleteByExpirationBefore(LocalDateTime dateTime);

}
