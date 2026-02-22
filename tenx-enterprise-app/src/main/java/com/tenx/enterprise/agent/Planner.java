package com.tenx.enterprise.agent;

import com.tenx.enterprise.dto.TaskEnvelope;

import java.util.List;

/**
 * Decomposes campaign goals into discrete parallel tasks for Workers.
 */
public interface Planner {

    List<TaskEnvelope> decompose(String campaignId, String goal);
}