package com.tenx.enterprise.dto;

import java.time.Instant;
import java.util.List;

/**
 * Immutable DTO representing generated content output from the ContentGenerator skill.
 * Contract: specs/technical.md → ContentPayload
 */
public record ContentPayload(
        String contentId,
        String script,
        String caption,
        List<String> hashtags,
        String platform,
        String persona,
        double estimatedCost,
        Instant generatedAt
) {
    public ContentPayload {
        if (contentId == null || contentId.isBlank())
            throw new IllegalArgumentException("contentId is required");
        if (script == null || script.isBlank())
            throw new IllegalArgumentException("script is required");
        if (caption == null || caption.isBlank())
            throw new IllegalArgumentException("caption is required");
        if (hashtags == null)
            throw new IllegalArgumentException("hashtags must not be null");
        if (platform == null || platform.isBlank())
            throw new IllegalArgumentException("platform is required");
        if (persona == null || persona.isBlank())
            throw new IllegalArgumentException("persona is required");
        if (estimatedCost < 0)
            throw new IllegalArgumentException("estimatedCost cannot be negative");
        if (generatedAt == null)
            throw new IllegalArgumentException("generatedAt is required");
    }
}