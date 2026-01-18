package com.java.Linkly.service;

import com.java.Linkly.ApplicationProperties;
import com.java.Linkly.entity.ShortUrl;
import com.java.Linkly.model.CreateShortUrlCmd;
import com.java.Linkly.model.PagedResult;
import com.java.Linkly.model.ShortUrlDto;
import com.java.Linkly.repo.ShortUrlRepo;
import com.java.Linkly.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ShortUrlService {
    private final ShortUrlRepo shortUrlRepo;
    private final UserRepo userRepo;
    private final EntityMapper entityMapper;
    private final ApplicationProperties properties;

    public PagedResult<ShortUrlDto> findAllPublicShortUrls(int pageNo, int pageSize) {
        pageNo = pageNo > 1? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ShortUrlDto> shortUrlDtoPage = shortUrlRepo.findPublicShortUrls(pageable)
                .map(entityMapper::toShortUrlDto);
        return PagedResult.from(shortUrlDtoPage);
    }

    @Transactional
    public ShortUrlDto createShortUrl(CreateShortUrlCmd cmd) {
        if(properties.validateOriginalUrl()) {
            boolean urlExists = UrlExistenceValidator.isUrlExists(cmd.originalUrl());
            if(!urlExists)
                throw new RuntimeException("Invalid URL" + cmd.originalUrl());
        }

        var shortKey = generateUniqueShortKey();
        var shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(cmd.originalUrl());
        shortUrl.setShortKey(shortKey);
        if(cmd.userId() == null) {
            shortUrl.setCreatedBy(null);
            shortUrl.setIsPrivate(false);
            shortUrl.setExpiresAt(Instant.now().plus(properties.defaultExpiryInDays(), DAYS));
        } else {
            shortUrl.setCreatedBy(userRepo.findById(cmd.userId()).orElseThrow());
            shortUrl.setIsPrivate(cmd.isPrivate() != null && cmd.isPrivate());
            shortUrl.setExpiresAt(cmd.expirationInDays() != null ? Instant.now().plus(cmd.expirationInDays(), DAYS) : null);
        }
        shortUrl.setClickCount(0L);
        shortUrl.setCreatedAt(Instant.now());
        shortUrlRepo.save(shortUrl);
        return entityMapper.toShortUrlDto(shortUrl);
    }

    private String generateUniqueShortKey() {
        String shortKey;
        do {
            shortKey = generateRandomShortKey();
        } while(shortUrlRepo.existsByShortKey(shortKey));
        return shortKey;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_KEY_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomShortKey() {
        StringBuilder sb = new StringBuilder(SHORT_KEY_LENGTH);
        for (int i = 0; i < SHORT_KEY_LENGTH; i++)
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        return sb.toString();
    }

    @Transactional
    public Optional<ShortUrlDto> getOriginalUrl(String shortKey, Long userId) {
        Optional<ShortUrl> shortUrlOptional = shortUrlRepo.findByShortKey(shortKey);
        if(shortUrlOptional.isEmpty())
            return Optional.empty();
        ShortUrl shortUrl = shortUrlOptional.get();
        if(shortUrl.getExpiresAt() != null && shortUrl.getExpiresAt().isBefore(Instant.now()))
            return Optional.empty();
        if(shortUrl.getIsPrivate() != null && shortUrl.getCreatedBy() != null
                && !shortUrl.getCreatedBy().getId().equals(userId))
            return Optional.empty();
        shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        shortUrlRepo.save(shortUrl);
        return shortUrlOptional.map(entityMapper::toShortUrlDto);
    }

    public PagedResult<ShortUrlDto> getUserUrls(Long userId, int page, int size) {
        page = page > 1? page - 1 : 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        var shortUrlsPage = shortUrlRepo.findByCreatedById(userId, pageable)
                .map(entityMapper::toShortUrlDto);
        return PagedResult.from(shortUrlsPage);
    }

    @Transactional
    public void deleteUserUrls(List<Long> ids, Long userId) {
        if(!ids.isEmpty() && userId != null)
            shortUrlRepo.deleteByIdInAndCreatedById(ids, userId);
    }

    public PagedResult<ShortUrlDto> findAllShortUrls(int page, int size) {
        page = page > 1 ? page - 1 : 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        var shortUrlsPage = shortUrlRepo.findAllShortUrls(pageable)
                .map(entityMapper::toShortUrlDto);
        return PagedResult.from(shortUrlsPage);
    }
}
