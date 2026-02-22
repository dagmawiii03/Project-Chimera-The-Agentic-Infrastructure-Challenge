# Skill: Generate Content

## Purpose
Generates short-form video content (script, metadata, and publishing instructions) based
on a selected trend and the influencer's persona configuration. This skill is invoked by
Worker agents after trend data has been fetched and a campaign TaskEnvelope has been
created by the Planner.

## Interface
`com.tenx.enterprise.skill.ContentGeneratorSkill`

## Input Contract
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| trendData | TrendData | Yes | The trend to base content on (from skill_fetch_trends) |
| personaId | String (UUID) | Yes | The influencer persona to use for tone/style |
| contentType | String | Yes | "short_video", "reel", or "story" |
| maxDurationSeconds | int | Yes | Maximum video duration (15, 30, or 60) |
| campaignId | String (UUID) | Yes | Parent campaign for budget tracking |
| languageCode | String | No | ISO 639-1 code, defaults to "en" |

## Output Contract
Returns `ContentOutput` record:
```json
{
  "contentId": "uuid",
  "campaignId": "uuid",
  "personaId": "uuid",
  "script": {
    "hook": "Did you know AI can now compose entire symphonies?",
    "body": "Three tools are changing music forever...",
    "callToAction": "Follow for more AI music breakdowns"
  },
  "metadata": {
    "title": "AI Music Revolution",
    "hashtags": ["#AIMusic", "#GenerativeAI", "#TechTrends"],
    "platform": "tiktok",
    "estimatedDurationSeconds": 28,
    "contentType": "short_video"
  },
  "confidenceLevel": "HIGH",
  "estimatedCostUsd": 0.03,
  "generatedAt": "2025-01-15T11:00:00Z",
  "version": 1
}