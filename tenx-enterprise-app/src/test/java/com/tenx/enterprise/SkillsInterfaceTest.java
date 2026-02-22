package com.tenx.enterprise;

import com.tenx.enterprise.dto.ContentPayload;
import com.tenx.enterprise.dto.TrendData;
import com.tenx.enterprise.exception.BudgetExceededException;
import com.tenx.enterprise.skill.ContentGeneratorSkill;
import com.tenx.enterprise.skill.TrendFetcherSkill;
import com.tenx.enterprise.skill.impl.ContentGeneratorSkillStub;
import com.tenx.enterprise.skill.impl.TrendFetcherSkillStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TDD tests for the Skill interfaces and cross-cutting concerns.
 *
 * Tests cover:
 *   - ContentGeneratorSkill behavior and budget enforcement
 *   - BudgetExceededException handling
 *   - Skill null/invalid parameter validation
 *   - Concurrent skill execution via Virtual Threads
 *
 * Contract: specs/technical.md → ContentGeneratorSkill, BudgetExceededException
 */
class SkillsInterfaceTest {

    private ContentGeneratorSkill contentGenerator;
    private TrendFetcherSkill trendFetcher;
    private TrendData sampleTrend;

    @BeforeEach
    void setUp() {
        contentGenerator = new ContentGeneratorSkillStub();
        trendFetcher = new TrendFetcherSkillStub();
        sampleTrend = new TrendData(
                "trend-001", "tiktok", "AI Coding",
                List.of("ai", "coding", "automation"),
                0.9, "US", Instant.now(), 1L
        );
    }

    // =========================================================================
    // CONTRACT TESTS — validate DTOs and exceptions (these pass now)
    // =========================================================================

    @Test
    @DisplayName("ContentPayload record should enforce validation on construction")
    void contentPayload_enforceValidation() {
        assertThrows(IllegalArgumentException.class, () ->
                        new ContentPayload(null, "script", "caption", List.of(), "tiktok", "guru", 1.0, Instant.now()),
                "Null contentId should throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () ->
                        new ContentPayload("c1", "", "caption", List.of(), "tiktok", "guru", 1.0, Instant.now()),
                "Blank script should throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () ->
                        new ContentPayload("c1", "script", "caption", List.of(), "tiktok", "guru", -5.0, Instant.now()),
                "Negative cost should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("BudgetExceededException carries requested and available amounts")
    void budgetExceededException_carriesAmounts() {
        BudgetExceededException ex = new BudgetExceededException(100.0, 25.0);

        assertEquals(100.0, ex.getRequested());
        assertEquals(25.0, ex.getAvailable());
        assertTrue(ex.getMessage().contains("100.00"), "Message should contain requested amount");
        assertTrue(ex.getMessage().contains("25.00"), "Message should contain available amount");
    }

    @Test
    @DisplayName("ContentGeneratorSkill should reject null trend input")
    void generateContent_nullTrend_throwsException() {
        assertThrows(Exception.class, () ->
                        contentGenerator.generateContent(null, "techGuru", 50.0),
                "Null trend input must be rejected");
    }

    @Test
    @DisplayName("ContentGeneratorSkill should reject null persona")
    void generateContent_nullPersona_throwsException() {
        assertThrows(Exception.class, () ->
                        contentGenerator.generateContent(sampleTrend, null, 50.0),
                "Null persona must be rejected");
    }

    // =========================================================================
    // BEHAVIORAL TESTS — these FAIL until stubs are replaced with real impls
    // =========================================================================

    @Test
    @DisplayName("TDD-FAIL: ContentGenerator must NOT be the stub")
    void contentGenerator_mustNotBeStub() {
        assertFalse(
                contentGenerator instanceof ContentGeneratorSkillStub,
                "TDD GOAL: Replace ContentGeneratorSkillStub with a real implementation " +
                "that generates meaningful content using an LLM or template engine."
        );
    }

    @Test
    @DisplayName("TDD-FAIL: Generated script must be substantial (min 50 chars)")
    void generateContent_scriptIsSubstantial() {
        ContentPayload result = contentGenerator.generateContent(sampleTrend, "techGuru", 50.0);

        assertNotNull(result, "ContentPayload must not be null");
        assertTrue(result.script().length() >= 50,
                "Real content script must be at least 50 characters for a meaningful video. " +
                "Got " + result.script().length() + " chars: '" + result.script() + "'");
    }

    @Test
    @DisplayName("TDD-FAIL: Generated hashtags must relate to the input trend")
    void generateContent_hashtagsRelatToTrend() {
        ContentPayload result = contentGenerator.generateContent(sampleTrend, "techGuru", 50.0);

        assertNotNull(result.hashtags(), "Hashtags must not be null");
        assertTrue(result.hashtags().size() >= 3,
                "Content must include at least 3 hashtags for discoverability, got: " +
                result.hashtags().size());

        boolean anyRelevant = result.hashtags().stream()
                .anyMatch(tag -> tag.toLowerCase().contains("ai") ||
                                 tag.toLowerCase().contains("coding") ||
                                 tag.toLowerCase().contains("tech"));
        assertTrue(anyRelevant,
                "At least one hashtag must relate to the trend topic '" +
                sampleTrend.topic() + "'. Got: " + result.hashtags());
    }

    @Test
    @DisplayName("TDD-FAIL: Budget enforcement — must throw BudgetExceededException for low budget")
    void generateContent_lowBudget_throwsBudgetExceeded() {
        assertThrows(BudgetExceededException.class, () ->
                        contentGenerator.generateContent(sampleTrend, "techGuru", 0.01),
                "Real implementation must enforce budget limits. " +
                "A $0.01 budget should not be enough to generate content.");
    }

    @Test
    @DisplayName("TDD-FAIL: estimatedCost must reflect realistic generation costs")
    void generateContent_estimatedCostIsRealistic() {
        ContentPayload result = contentGenerator.generateContent(sampleTrend, "techGuru", 50.0);

        assertTrue(result.estimatedCost() > 0.0,
                "Real implementation must report a non-zero cost for content generation. " +
                "Got: $" + result.estimatedCost());
    }

    @Test
    @DisplayName("TDD-FAIL: Skills must execute safely under Virtual Thread concurrency")
    void skills_concurrentExecution_isThreadSafe() throws Exception {
        int taskCount = 100;

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<? extends Future<ContentPayload>> futures = executor.invokeAll(
                    java.util.Collections.nCopies(taskCount, () ->
                            contentGenerator.generateContent(sampleTrend, "techGuru", 50.0)
                    ),
                    10, TimeUnit.SECONDS
            );

            long successCount = futures.stream()
                    .filter(f -> {
                        try {
                            ContentPayload p = f.get();
                            return p != null && p.contentId() != null && !p.contentId().isBlank();
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .count();

            assertEquals(taskCount, successCount,
                    "All " + taskCount + " concurrent Virtual Thread executions must succeed. " +
                    "Only " + successCount + " succeeded — check thread safety.");
        }
    }
}