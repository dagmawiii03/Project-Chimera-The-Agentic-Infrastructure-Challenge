package com.tenx.enterprise.dto;

import java.time.Instant;

/**
 * Immutable verdict issued by the Judge after evaluating a ContentArtifact.
 * Contract: specs/technical.md → JudgeVerdict
 */
public record JudgeVerdict(
        String verdictId,
        String artifactId,
        ConfidenceLevel confidence,
        boolean sensitiveTopicDetected,
        boolean approved,
        String reason,
        Instant judgedAt
) {}