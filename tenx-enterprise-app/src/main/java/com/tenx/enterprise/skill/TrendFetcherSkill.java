package com.tenx.enterprise.skill;

import com.tenx.enterprise.dto.TaskType;
import com.tenx.enterprise.dto.TrendData;

import java.util.List;

/**
 * Skill contract for fetching platform trends.
 * See: skills/skill_fetch_trends/README.md
 */
public interface TrendFetcherSkill {

    String name();

    List<TrendData> fetchTrends(String platform, String region, int limit);

    boolean isAvailable();

    boolean supports(TaskType taskType);
}