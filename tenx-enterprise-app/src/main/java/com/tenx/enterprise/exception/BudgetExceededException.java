package com.tenx.enterprise.exception;

/**
 * Thrown when a skill execution would exceed the allocated campaign budget.
 * Contract: specs/functional.md → US-006 (Budget Governance)
 */
public class BudgetExceededException extends RuntimeException {

    private final double requested;
    private final double available;

    public BudgetExceededException(double requested, double available) {
        super(String.format(
                "Budget exceeded: requested $%.2f but only $%.2f available", requested, available));
        this.requested = requested;
        this.available = available;
    }

    public double getRequested() {
        return requested;
    }

    public double getAvailable() {
        return available;
    }
}