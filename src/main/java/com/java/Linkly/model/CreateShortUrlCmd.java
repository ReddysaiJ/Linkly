package com.java.Linkly.model;

public record CreateShortUrlCmd(String originalUrl, Boolean isPrivate,
                                Integer expirationInDays, Long userId) {
}
