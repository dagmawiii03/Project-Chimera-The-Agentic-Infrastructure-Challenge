package com.tenx.enterprise.dto;

/**
 * Enum of task types a Worker can execute.
 * Contract: specs/technical.md → TaskEnvelope.type
 */
public enum TaskType {
    TREND_RESEARCH,
    CONTENT_GENERATION,
    ENGAGEMENT,
    FINANCIAL_CHECK
}