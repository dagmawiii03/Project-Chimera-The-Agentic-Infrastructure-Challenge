# Skills Directory — Project Chimera

## Overview
This directory contains the **Agent Runtime Skill** definitions for Project Chimera. Each
subdirectory represents a discrete capability that the autonomous influencer agent can
invoke during operation.

Skills are **not** developer tools. They are runtime capabilities executed by Worker agents
as directed by the Planner. See `research/tooling_strategy.md` for the distinction between
Dev MCP Servers and Runtime Skills.

---

## Skill Index

| Skill | Directory | Description | Status |
|-------|-----------|-------------|--------|
| Fetch Trends | `skill_fetch_trends/` | Retrieves trending topics from social platforms | Contract defined |
| Generate Content | `skill_generate_content/` | Produces short-form video scripts and metadata | Contract defined |

---

## Skill Contract Structure

Every skill README defines:

1. **Purpose** — What the skill does and when the Planner should invoke it.
2. **Interface** — The fully qualified Java interface name.
3. **Input Contract** — Parameters with types, constraints, and required/optional status.
4. **Output Contract** — The return type with a JSON example matching `specs/technical.md`.
5. **Error Handling** — Domain exceptions the skill may throw.

---

## Adding a New Skill

1. Create a new directory: `skills/<skill_name>/`
2. Write `README.md` with the full I/O contract.
3. Create the Java interface in `src/main/java/com/tenx/enterprise/skill/`.
4. Create a stub implementation in `src/main/java/com/tenx/enterprise/skill/impl/`.
5. Write failing JUnit 5 tests in `src/test/java/com/tenx/enterprise/`.
6. Update this index.

---

## Design Principles

- **Spec-First:** The README contract is written before any Java code.
- **Immutable I/O:** All inputs and outputs are Java Records (immutable DTOs).
- **Fail Loudly:** Skills throw domain-specific exceptions, never raw RuntimeException.
- **Testable:** Every skill interface is mockable; stubs exist for TDD.
- **Stateless:** Skills do not hold internal state between invocations. State is carried
  via TaskEnvelope and managed by the Planner/Judge.