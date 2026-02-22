package com.tenx.enterprise.dto;

import java.time.Instant;
import java.util.List;

/**
 * Immutable DTO for trend data fetched from social platforms.
 * Contract: specs/technical.md → TrendData
 */
public record TrendData(
        String trendId,
        String platform,
        String topic,
        List<String> keywords,
        double relevanceScore,
        String region,
        Instant fetchedAt,
        long version
) {
    public TrendData {
        if (trendId == null || trendId.isBlank()) {
            throw new IllegalArgumentException("trendId must not be blank");
        }
        if (platform == null || platform.isBlank()) {
            throw new IllegalArgumentException("platform must not be blank");
        }
        if (relevanceScore < 0.0 || relevanceScore > 1.0) {
            throw new IllegalArgumentException("relevanceScore must be between 0.0 and 1.0");
        }
    }
}