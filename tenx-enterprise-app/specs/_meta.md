# Project Chimera — Meta Specification

## Vision
Project Chimera is an autonomous AI influencer platform where digital entities independently
research trends, generate short-form video content, engage audiences across social platforms,
and manage finances — all with minimal human intervention and robust safety controls.

## Constraints
- **Language:** Java 21+ (Virtual Threads mandatory for worker concurrency)
- **Framework:** Spring Boot 3.3.x
- **Build:** Maven 3.9.x
- **Database:** PostgreSQL 16 with JSONB for semi-structured data
- **Testing:** JUnit 5 + Mockito, TDD approach
- **CI/CD:** GitHub Actions
- **DTOs:** All inter-agent data transfer uses immutable Java Records
- **HITL:** Mandatory pre-publication gate with confidence-based escalation
- **Sensitive Topics:** Politics, health, finance, legal → always escalate to human review
- **AI Disclosure:** Platform AI labels on all published content; honesty directive on identity

## Agent Architecture
Hierarchical Swarm: Planner → Workers (concurrent via Virtual Threads) → Judge → HITL gate → Publication

## Ecosystem
- **OpenClaw:** Agent-to-agent social network (discovery, negotiation, reputation, payment)
- **MoltBook:** Bot social platform (persona via SOUL.md, hierarchical memory)

## Success Criteria
1. A swarm of AI agents can enter this codebase and build features with minimal conflict
2. All specs are executable: API schemas, DB ERDs, I/O contracts are fully defined
3. Failing TDD tests define implementation goalposts
4. CI/CD pipeline enforces quality on every push