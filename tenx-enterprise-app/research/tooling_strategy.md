# Tooling Strategy — Project Chimera

## Overview
Project Chimera distinguishes between two categories of tools: **Developer MCP Servers**
(tools that help the human/AI developer build the system) and **Agent Runtime Skills**
(capabilities the Chimera agent invokes at runtime to accomplish tasks). This separation
ensures that development concerns never leak into production agent behavior, and vice versa.

---

## Category A: Developer MCP Servers

Developer MCP servers are external bridges that augment the IDE-based AI co-pilot during
development. They are **never** deployed as part of the production agent runtime.

### 1. Tenx MCP Sense
- **Purpose:** Flight recorder / telemetry. Captures all AI reasoning, code generation
  decisions, and developer interactions. Acts as the "black box" for traceability and
  assessment verification.
- **Connection:** Always-on during development sessions. Connected via IDE extension
  (Cursor / VS Code).
- **Why:** Required by project rules. Enables post-hoc audit of every architectural decision.

### 2. git-mcp
- **Purpose:** Version control operations from within the AI agent context. Enables the
  co-pilot to stage, commit, diff, and inspect history without leaving the conversation.
- **Operations:** `git status`, `git diff`, `git log`, `git add`, `git commit`
- **Why:** Enforces Git Hygiene (minimum 2 commits/day). Lets the agent verify it is not
  overwriting uncommitted work.

### 3. filesystem-mcp
- **Purpose:** File system read/write operations. Allows the AI co-pilot to create, read,
  update, and delete project files directly.
- **Operations:** `read_file`, `write_file`, `list_directory`, `search_files`
- **Why:** Enables spec-first workflow — the agent reads `specs/` before generating any
  code, satisfying the Prime Directive.

### 4. fetch-mcp (optional, future)
- **Purpose:** HTTP requests during development for testing external API integrations
  (e.g., verifying TikTok API response shapes, OpenClaw endpoint schemas).
- **Why:** Reduces context-switching between IDE and browser/Postman during
  API contract validation.

---

## Category B: Agent Runtime Skills

Runtime Skills are capability packages that the Chimera agent invokes during autonomous
operation. Each skill is a self-contained unit with a defined Input/Output contract, living
in the `skills/` directory. Skills are implemented as Java interfaces with pluggable
implementations (stubs for TDD, real implementations for production).

### Skill Registry (Current)

| Skill ID | Directory | Interface | Status |
|----------|-----------|-----------|--------|
| `skill_fetch_trends` | `skills/skill_fetch_trends/` | `TrendFetcherSkill` | Contract defined, stub implemented |
| `skill_generate_content` | `skills/skill_generate_content/` | `ContentGeneratorSkill` | Contract defined, stub pending |

### Skill Architecture

Each skill follows a uniform structure:

The corresponding Java interface lives at `src/main/java/com/tenx/enterprise/skill/` and
the stub implementation at `src/main/java/com/tenx/enterprise/skill/impl/`.

### Skill Lifecycle

1. **Definition:** README.md with Input/Output contract is written first (spec-driven).
2. **Interface:** Java interface is created matching the contract.
3. **Stub:** A no-op or mock implementation is created so tests can compile.
4. **TDD Tests:** Failing tests are written that assert contract compliance.
5. **Implementation:** Real logic is filled in (by human or AI agent) until tests pass.

### Future Skills (Planned)

| Skill ID | Purpose | Priority |
|----------|---------|----------|
| `skill_transcribe_audio` | Audio-to-text via Whisper API | Medium |
| `skill_download_video` | Download reference videos from platforms | Medium |
| `skill_publish_content` | Post generated content to social platforms | High |
| `skill_manage_budget` | Track and enforce per-campaign spending limits | High |
| `skill_openclaw_status` | Publish agent availability to OpenClaw network | Low |

---

## Integration Points

### Dev Tools → Specs
The developer (aided by MCP servers) writes and maintains specs. The filesystem-mcp
reads specs before any code generation. The git-mcp ensures specs are committed before
implementation begins.

### Specs → Runtime Skills
Each skill's README.md is the bridge between the spec layer and the implementation layer.
The `specs/technical.md` defines the canonical JSON schemas; the skill READMEs
reference those schemas for their I/O contracts.

### Runtime Skills → Agent Roles
The Planner decomposes campaigns into TaskEnvelopes. Each TaskEnvelope references a
skill ID. Workers execute the referenced skill. The Judge validates the output against the
skill's output contract and the campaign spec.

---

## Decision Log

| Decision | Rationale |
|----------|-----------|
| PostgreSQL over MongoDB | Relational integrity for campaigns/personas; JSONB gives document flexibility where needed |
| Caffeine over Redis (for now) | Simpler local dev; interface abstraction allows Redis swap later |
| Skill-as-interface pattern | Enables TDD with stubs; pluggable implementations; mockable in tests |
| MCP for dev only, not runtime | Clean separation; production agent should not depend on IDE tooling |