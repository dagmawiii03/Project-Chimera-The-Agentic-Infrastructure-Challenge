package com.tenx.enterprise.dto;

/**
 * Judge-assigned confidence tiers for HITL routing.
 * HIGH  → auto-approved for publication
 * MEDIUM → queued for asynchronous human review
 * LOW   → rejected, returned to Planner for rework
 */
public enum ConfidenceLevel {
    HIGH,
    MEDIUM,
    LOW
}