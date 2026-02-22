package com.tenx.enterprise.dto;

import java.time.Instant;

/**
 * Immutable DTO for content produced by a Worker.
 * Contract: specs/technical.md → ContentArtifact
 */
public record ContentArtifact(
        String artifactId,
        String taskId,
        String campaignId,
        String contentType,
        String contentBody,
        String platform,
        Instant generatedAt,
        long version
) {}