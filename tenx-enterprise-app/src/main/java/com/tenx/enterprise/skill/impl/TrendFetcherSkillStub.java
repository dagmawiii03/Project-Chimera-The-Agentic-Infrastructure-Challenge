package com.tenx.enterprise.skill.impl;

import com.tenx.enterprise.dto.TaskType;
import com.tenx.enterprise.dto.TrendData;
import com.tenx.enterprise.skill.TrendFetcherSkill;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Stub implementation that returns simulated trend data.
 * In production, this would call real platform APIs.
 */
public class TrendFetcherSkillStub implements TrendFetcherSkill {

    private static final String[][] SIMULATED_TRENDS = {
        {"AI Tools", "ai,tools,automation"},
        {"Short Form Video", "shorts,reels,video"},
        {"Fitness Challenge", "fitness,health,challenge"},
        {"DIY Crafts", "diy,crafts,handmade"},
        {"Tech Reviews", "tech,reviews,gadgets"},
    };

    @Override
    public String name() {
        return "skill_fetch_trends";
    }

    @Override
    public List<TrendData> fetchTrends(String platform, String region, int limit) {
        List<TrendData> results = new ArrayList<>();
        int count = Math.min(limit, SIMULATED_TRENDS.length);

        for (int i = 0; i < count; i++) {
            String[] trend = SIMULATED_TRENDS[i];
            results.add(new TrendData(
                UUID.randomUUID().toString(),
                platform,
                trend[0],
                List.of(trend[1].split(",")),
                Math.round((0.95 - i * 0.1) * 100.0) / 100.0,
                region,
                Instant.now(),
                1L
            ));
        }

        return results;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean supports(TaskType taskType) {
        return taskType == TaskType.TREND_RESEARCH;
    }
}
