package com.tenx.enterprise.skill.impl;

import com.tenx.enterprise.dto.ContentPayload;
import com.tenx.enterprise.dto.TrendData;
import com.tenx.enterprise.exception.BudgetExceededException;
import com.tenx.enterprise.skill.ContentGeneratorSkill;

import java.time.Instant;
import java.util.List;

/**
 * Stub implementation of ContentGeneratorSkill.
 * Returns minimal placeholder data. Does NOT enforce budget limits.
 * TDD: Tests will FAIL against this stub until replaced with real implementation.
 */
public class ContentGeneratorSkillStub implements ContentGeneratorSkill {

    @Override
    public ContentPayload generateContent(TrendData trend, String persona, double budget)
            throws BudgetExceededException {
        // Stub: no budget checking, returns placeholder content
        return new ContentPayload(
                "stub-content-001",
                "Stub script placeholder",
                "Stub caption",
                List.of("stub"),
                trend.platform(),
                persona,
                0.0,
                Instant.now()
        );
    }
}