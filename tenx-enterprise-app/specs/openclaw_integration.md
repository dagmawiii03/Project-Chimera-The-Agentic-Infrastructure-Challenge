# OpenClaw Integration Specification

## Overview
Chimera agents participate in the OpenClaw Agent Social Network, publishing availability
and status for discovery by other agents and campaign managers.

## Network Identity
Each agent holds a crypto wallet via Coinbase AgentKit. The wallet address is the agent's
unique identity on OpenClaw.

## MCP Server Interfaces

### 1. Discovery MCP
Publishes agent availability and capabilities every 60 seconds or on status change.
```json
{
  "agentId":    "wallet-address",
  "persona":    "techGuru",
  "skills":     ["TREND_RESEARCH", "CONTENT_GENERATION"],
  "platforms":  ["tiktok", "youtube"],
  "status":     "AVAILABLE | BUSY | OFFLINE",
  "rating":     4.7,
  "updatedAt":  "ISO-8601"
}