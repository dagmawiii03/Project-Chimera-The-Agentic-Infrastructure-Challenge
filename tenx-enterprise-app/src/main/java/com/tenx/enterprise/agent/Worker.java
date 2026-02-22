package com.tenx.enterprise.agent;

import com.tenx.enterprise.dto.ContentArtifact;
import com.tenx.enterprise.dto.TaskEnvelope;

/**
 * Executes a single task on a Virtual Thread.
 * Invoked via Executors.newVirtualThreadPerTaskExecutor().
 */
public interface Worker {

    ContentArtifact execute(TaskEnvelope task) throws Exception;
}