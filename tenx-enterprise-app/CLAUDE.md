# CLAUDE.md — Project Chimera AI Agent Rules

## Project Context
This is **Project Chimera**, an autonomous AI influencer platform built on Java 21+ with
Virtual Threads, Spring Boot 3.3.x, and Maven. The system enables digital entities (AI
influencers) to research trends, generate short-form video content, engage audiences, and
manage finances with minimal human intervention.

---

## Prime Directive
**NEVER generate code without checking `specs/` first.** All implementation must trace
back to a specification in the `specs/` directory. If no spec exists for a requested feature,
create or update the spec before writing any code.

---

## Architecture Overview
- **Pattern:** Hierarchical Swarm — Planner, Worker, Judge
- **Planner:** Decomposes campaign goals into discrete `TaskEnvelope` records
- **Workers:** Execute tasks concurrently via `Executors.newVirtualThreadPerTaskExecutor()`
- **Judge:** Validates every output against persona rules, safety filters, and campaign specs;
  assigns `ConfidenceLevel` (HIGH → auto-publish, MEDIUM → human review queue,
  LOW → reject and return to Planner)
- **HITL:** Post-generation, pre-publication with confidence-based escalation. Sensitive
  topics (politics, health, finance, legal) always mandate human review regardless of
  confidence score.
- **State Consistency:** Optimistic Concurrency Control (OCC) — every DTO carries a
  `version` field; the Judge rejects stale submissions which are then requeued.

---

## Java-Specific Directives
1. **Java 21+** — Use modern idioms: Records, sealed interfaces, pattern matching,
   Virtual Threads. Never use legacy Thread class or fixed thread pools for Worker execution.
2. **Records for DTOs** — ALL data transfer objects between Planner/Worker/Judge MUST
   be Java Records (immutable). Never use mutable POJOs for inter-agent communication.
   See `specs/technical.md` for exact Record definitions.
3. **Virtual Threads** — Use `Executors.newVirtualThreadPerTaskExecutor()` for Worker
   concurrency. This enables thousands of lightweight concurrent tasks.
4. **Testing** — JUnit 5 with Mockito. Follow TDD: failing tests define the implementation
   goalposts. Tests live in `src/test/java/com/tenx/enterprise/`.
5. **Exception Hierarchy** — Use domain exceptions (`BudgetExceededException`,
   `StaleVersionException`, `SkillExecutionException`). Never throw raw RuntimeException.
6. **Traceability** — Explain your plan before writing code. Reference the relevant spec
   section. After writing code, verify alignment with specs.

---

## Database Strategy
- PostgreSQL 16 as single database
- Relational columns for structured data (campaigns, agents, personas, reviews)
- JSONB columns for semi-structured data (trends, video metadata, platform-specific fields)
- GIN indexes on JSONB columns
- Short-term memory: Caffeine in-memory cache (interfaced for future Redis swap)
- Long-term memory: Java interface with stub (ready for Weaviate vector DB)

---

## Key Directories
| Directory | Purpose |
|-----------|---------|
| `specs/` | Source of truth — functional, technical, meta specs |
| `skills/` | Agent runtime skill definitions with I/O contracts |
| `research/` | Architecture strategy and tooling research |
| `src/main/java/**/dto/` | Immutable Java Records (TrendData, TaskEnvelope, etc.) |
| `src/main/java/**/skill/` | Skill interfaces + stub implementations |
| `src/main/java/**/agent/` | Agent role interfaces (Planner, Worker, Judge) |
| `src/main/java/**/exception/` | Domain exceptions |
| `src/test/java/` | JUnit 5 tests (TDD — currently failing by design) |

---

## Ecosystem Integration
- **OpenClaw:** Crypto wallet (Coinbase AgentKit) as network identity. Four MCP server
  interfaces: discovery, negotiation, reputation, payment. See `specs/openclaw_integration.md`.
- **MoltBook:** SOUL.md defines persona. Honesty directive overrides persona when asked
  about AI nature. Platform AI labels on all published posts.

---

## Dev Tools vs. Runtime Skills
| Category | Examples | Purpose |
|----------|----------|---------|
| Dev MCP Servers | git-mcp, filesystem-mcp, Tenx MCP Sense | Help the developer build |
| Runtime Skills | skill_fetch_trends, skill_generate_content | Agent capabilities at runtime |

See `research/tooling_strategy.md` for full tooling documentation.

---

## Build Commands
```bash
make setup   # mvn clean install -DskipTests
make test    # mvn test (expect failures — TDD stubs)
make lint    # mvn checkstyle:check