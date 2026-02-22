package com.tenx.enterprise.agent;

import com.tenx.enterprise.dto.ContentArtifact;
import com.tenx.enterprise.dto.JudgeVerdict;

/**
 * Validates artifacts against persona rules, safety filters, and campaign specs.
 * Assigns ConfidenceLevel and routes to HITL gate.
 */
public interface Judge {

    JudgeVerdict evaluate(ContentArtifact artifact);
}