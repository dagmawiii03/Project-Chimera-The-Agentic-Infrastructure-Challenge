package com.tenx.enterprise;

public class TenxMcpConnection {
    public static void main(String[] args) {
        try {
            MockMcpClient client = new MockMcpClient();
            boolean connected = client.testConnection();
            System.out.println("✅ TenX MCP Connection: " + (connected ? "SUCCESS" : "FAILURE"));
        } catch (Exception e) {
            System.err.println("❌ TenX MCP Connection failed: " + e.getMessage());
        }
    }
}