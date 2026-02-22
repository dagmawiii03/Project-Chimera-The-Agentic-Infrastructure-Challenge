# Project Chimera — Technical Specification

## API Contracts (Java Records)

### TrendData
```json
{
  "trendId":        "string (non-blank, required)",
  "platform":       "string (non-blank, required)",
  "topic":          "string",
  "keywords":       ["string"],
  "relevanceScore": "double (0.0–1.0, required)",
  "region":         "string",
  "fetchedAt":      "ISO-8601 Instant",
  "version":        "long (OCC version field)"
}