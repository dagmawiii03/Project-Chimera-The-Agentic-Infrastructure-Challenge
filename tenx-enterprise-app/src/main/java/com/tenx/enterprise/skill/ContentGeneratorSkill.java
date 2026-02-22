package com.tenx.enterprise.skill;

import com.tenx.enterprise.dto.ContentPayload;
import com.tenx.enterprise.dto.TrendData;
import com.tenx.enterprise.exception.BudgetExceededException;

/**
 * Skill interface for generating social media content from trend data.
 * Contract: specs/technical.md → ContentGeneratorSkill
 */
public interface ContentGeneratorSkill {

    /**
     * Generates content based on a trend, persona strategy, and budget constraint.
     *
     * @param trend   the trend to base content on
     * @param persona the influencer persona (e.g., "techGuru")
     * @param budget  maximum allowed spend in USD
     * @return a ContentPayload with the generated script, caption, and hashtags
     * @throws BudgetExceededException if the generation cost would exceed budget
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    ContentPayload generateContent(TrendData trend, String persona, double budget)
            throws BudgetExceededException;
}