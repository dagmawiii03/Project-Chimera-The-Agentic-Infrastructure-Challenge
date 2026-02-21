package com.tenx.enterprise;

/**
 * Mock MCP client for TenX challenge.
 * Simulates a connection to MCP Sense.
 */
public class MockMcpClient {

    public MockMcpClient() {
        // No-op constructor
    }

    /**
     * Simulate a successful connection.
     */
    public boolean testConnection() {
        return true;
    }
}