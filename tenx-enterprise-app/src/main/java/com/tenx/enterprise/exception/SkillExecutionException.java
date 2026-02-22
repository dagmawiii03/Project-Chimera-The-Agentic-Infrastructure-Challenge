package com.tenx.enterprise.exception;

/**
 * Generic wrapper for failures during skill execution.
 */
public class SkillExecutionException extends Exception {

    private final String skillName;

    public SkillExecutionException(String skillName, String message) {
        super("Skill [%s] failed: %s".formatted(skillName, message));
        this.skillName = skillName;
    }

    public SkillExecutionException(String skillName, String message, Throwable cause) {
        super("Skill [%s] failed: %s".formatted(skillName, message), cause);
        this.skillName = skillName;
    }

    public String getSkillName() {
        return skillName;
    }
}