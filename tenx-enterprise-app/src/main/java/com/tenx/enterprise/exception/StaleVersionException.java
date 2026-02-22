package com.tenx.enterprise.exception;

/**
 * Thrown by the Judge when a Worker submits a result with an outdated OCC version.
 */
public class StaleVersionException extends Exception {

    private final long expected;
    private final long actual;

    public StaleVersionException(long expected, long actual) {
        super("Stale version: expected %d but got %d".formatted(expected, actual));
        this.expected = expected;
        this.actual = actual;
    }

    public long getExpected() {
        return expected;
    }

    public long getActual() {
        return actual;
    }
}