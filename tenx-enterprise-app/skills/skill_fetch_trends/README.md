# Skill: Fetch Trends

## Purpose
Fetches current trending topics from social media platforms to identify content opportunities.

## Interface
`com.tenx.enterprise.skill.TrendFetcherSkill`

## Input Contract
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| platform | String | Yes | "tiktok", "youtube", or "instagram" |
| region | String | Yes | ISO region code: "US", "EU", "GLOBAL" |
| limit | int | Yes | Max results to return (1–50) |

## Output Contract
Returns `List<TrendData>` — each element:
```json
{
  "trendId": "uuid",
  "platform": "tiktok",
  "topic": "AI-generated music",
  "keywords": ["ai", "music", "generative"],
  "relevanceScore": 0.87,
  "region": "US",
  "fetchedAt": "2025-01-15T10:30:00Z",
  "version": 1
}