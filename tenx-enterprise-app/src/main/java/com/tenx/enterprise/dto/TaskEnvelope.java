package com.tenx.enterprise.dto;

import java.time.Instant;

/**
 * Immutable task wrapper created by the Planner, executed by Workers.
 * Carries OCC version field for concurrency control.
 * Contract: specs/technical.md → TaskEnvelope
 */
public record TaskEnvelope(
        String taskId,
        String campaignId,
        TaskType type,
        String payload,
        long version,
        Instant createdAt
) {}