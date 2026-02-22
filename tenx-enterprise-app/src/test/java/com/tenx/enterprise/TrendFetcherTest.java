package com.tenx.enterprise;

import com.tenx.enterprise.dto.TrendData;
import com.tenx.enterprise.skill.TrendFetcherSkill;
import com.tenx.enterprise.skill.impl.TrendFetcherSkillStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TDD tests for the TrendFetcher skill.
 *
 * Split into two groups:
 *   PASS  — Contract tests validating the TrendData Record itself.
 *   FAIL  — Behavioral tests that require a REAL implementation (not the stub).
 *
 * Contract: specs/technical.md → TrendData, TrendFetcherSkill
 */
class TrendFetcherTest {

    private TrendFetcherSkill trendFetcher;

    @BeforeEach
    void setUp() {
        trendFetcher = new TrendFetcherSkillStub();
    }

    // =========================================================================
    // CONTRACT TESTS — validate the TrendData Record (these pass now)
    // =========================================================================

    @Test
    @DisplayName("TrendData record should enforce validation on construction")
    void trendData_enforceValidation() {
        assertThrows(IllegalArgumentException.class, () ->
                        new TrendData(null, "tiktok", "test", List.of("a"), 0.5, "US", Instant.now(), 1L),
                "Null trendId should throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () ->
                        new TrendData("t1", "", "test", List.of("a"), 0.5, "US", Instant.now(), 1L),
                "Blank platform should throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () ->
                        new TrendData("t1", "tiktok", "test", List.of("a"), 1.5, "US", Instant.now(), 1L),
                "relevanceScore > 1.0 should throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () ->
                        new TrendData("t1", "tiktok", "test", List.of("a"), -0.1, "US", Instant.now(), 1L),
                "relevanceScore < 0.0 should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("TrendData record is immutable — fields match construction values")
    void trendData_isImmutable() {
        Instant now = Instant.now();
        TrendData trend = new TrendData(
                "t1", "tiktok", "AI", List.of("ai", "tech"), 0.8, "US", now, 1L);

        assertEquals("t1", trend.trendId());
        assertEquals("tiktok", trend.platform());
        assertEquals("AI", trend.topic());
        assertEquals(List.of("ai", "tech"), trend.keywords());
        assertEquals(0.8, trend.relevanceScore());
        assertEquals("US", trend.region());
        assertEquals(now, trend.fetchedAt());
        assertEquals(1L, trend.version());
    }

    // =========================================================================
    // BEHAVIORAL TESTS — these FAIL until stub is replaced with real impl
    // =========================================================================

    @Test
    @DisplayName("TDD-FAIL: Real implementation must replace the stub")
    void implementation_mustNotBeStub() {
        assertFalse(
                trendFetcher instanceof TrendFetcherSkillStub,
                "TDD GOAL: Replace TrendFetcherSkillStub with a real implementation " +
                "that fetches live trend data from an external source."
        );
    }

    @Test
    @DisplayName("TDD-FAIL: Different regions must return different trend data")
    void fetchTrends_differentRegions_returnDifferentResults() {
        List<TrendData> usTrends = trendFetcher.fetchTrends("tiktok", "US", 5);
        List<TrendData> jpTrends = trendFetcher.fetchTrends("tiktok", "JP", 5);

        Set<String> usIds = usTrends.stream()
                .map(TrendData::trendId)
                .collect(Collectors.toSet());
        Set<String> jpIds = jpTrends.stream()
                .map(TrendData::trendId)
                .collect(Collectors.toSet());

        assertNotEquals(usIds, jpIds,
                "Real implementation must return region-specific trends. " +
                "US and JP must not have identical trend IDs.");
    }

    @Test
    @DisplayName("TDD-FAIL: Consecutive calls must return evolving data")
    void fetchTrends_consecutiveCalls_returnUpdatedData() {
        List<TrendData> first = trendFetcher.fetchTrends("tiktok", "US", 5);
        List<TrendData> second = trendFetcher.fetchTrends("tiktok", "US", 5);

        boolean anyDifference = false;
        for (int i = 0; i < Math.min(first.size(), second.size()); i++) {
            if (!first.get(i).trendId().equals(second.get(i).trendId()) ||
                    first.get(i).relevanceScore() != second.get(i).relevanceScore()) {
                anyDifference = true;
                break;
            }
        }

        assertTrue(anyDifference,
                "Real implementation must return fresh data on each call. " +
                "Static stub data with identical IDs and scores is not acceptable.");
    }

    @Test
    @DisplayName("TDD-FAIL: Each trend must carry at least 3 substantive keywords")
    void fetchTrends_keywordsAreSubstantive() {
        List<TrendData> trends = trendFetcher.fetchTrends("tiktok", "US", 5);

        assertFalse(trends.isEmpty(), "Must return at least one trend");
        for (TrendData t : trends) {
            assertTrue(t.keywords().size() >= 3,
                    "Real trends should have at least 3 keywords for effective content " +
                    "generation, got " + t.keywords().size() + " for trend: " + t.trendId());
        }
    }
}