# 🧬 Project Chimera — Agentic Content Generation Infrastructure

> A Java 21 agentic infrastructure for automated social media content generation, built with a test-driven development (TDD) approach. This project defines the foundational skill contracts, self-validating data transfer objects, and behavioral goalposts for an AI-powered system that monitors trends and generates platform-tailored content for influencer personas — all under budget governance.

---

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [TDD Approach](#tdd-approach)
- [Running Tests](#running-tests)
- [Expected Test Results](#expected-test-results)
- [Demo Guide](#demo-guide)
- [Author](#author)

---

## Overview

Project Chimera is an agentic system where AI "skills" collaborate to perform two core functions:

**Trend Fetching** — Monitor social media platforms (TikTok, Instagram, YouTube) for trending topics in specific regions, returning structured trend data with relevance scoring and keyword extraction.

**Content Generation** — Take a trend and an influencer persona, then produce a tailored content payload (script, caption, hashtags) while respecting a campaign budget. If the estimated generation cost exceeds the available budget, the system raises a governed exception carrying both the requested and available amounts.

The project is currently in the **TDD Red Phase** — all skill contracts, data structures, and validation rules are implemented and tested. Behavioral tests have been written as goalposts that define exactly what the real AI-powered implementations must achieve. Stub implementations are in place to validate the architecture, and they intentionally fail the behavioral tests.

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Language runtime — leveraging records, compact constructors, and virtual threads |
| Maven | Build and dependency management |
| JUnit 5 | Testing framework |
| Java Records | Immutable, self-validating DTOs |
| Virtual Threads | Concurrency model for parallel skill execution |

---

## Architecture

The system follows a clean layered architecture with clear separation of concerns:

**DTOs (Data Transfer Objects)** are Java 21 records that enforce their own validity at construction time. `TrendData` represents a social media trend with fields including trendId, platform, topic, keywords, relevanceScore (constrained between 0.0 and 1.0), region, and fetchedAt. `ContentPayload` represents generated content with a script, caption, hashtags, platform, persona, and estimatedCost (must be non-negative). Both records throw `IllegalArgumentException` if constructed with invalid data, meaning malformed data physically cannot exist in the system.

**Skill Interfaces** define the behavioral contracts that any implementation must fulfill. `TrendFetcherSkill` declares a single method `fetchTrends(platform, region, limit)` that returns a list of `TrendData`. `ContentGeneratorSkill` declares `generateContent(trend, persona, budget)` that returns a `ContentPayload` or throws `BudgetExceededException`. These interfaces are the "what" — they specify system behavior without dictating how it is achieved.

**Stub Implementations** are placeholder implementations that return hardcoded, minimal data. `TrendFetcherSkillStub` returns the same static trends regardless of region. `ContentGeneratorSkillStub` returns a 23-character placeholder script, one generic hashtag, zero cost, and ignores the budget entirely. They exist so the project compiles and contract tests can execute, but they deliberately do not satisfy the behavioral requirements.

**Custom Exception** `BudgetExceededException` is a checked exception that carries both the `requestedAmount` and `availableAmount`, enabling callers to make informed decisions when a campaign exceeds its budget.

---

## Project Structure

project-chimera/
├── pom.xml
├── README.md
└── src/
├── main/java/com/chimera/
│ ├── dto/
│ │ ├── TrendData.java # Self-validating trend record
│ │ └── ContentPayload.java # Self-validating content record
│ ├── skill/
│ │ ├── TrendFetcherSkill.java # Trend fetching contract
│ │ └── ContentGeneratorSkill.java # Content generation contract
│ ├── stub/
│ │ ├── TrendFetcherSkillStub.java # Placeholder trend fetcher
│ │ └── ContentGeneratorSkillStub.java # Placeholder content generator
│ └── exception/
│ └── BudgetExceededException.java # Budget governance exception
└── test/java/com/chimera/
├── dto/
│ ├── TrendDataTest.java # Contract tests for TrendData
│ └── ContentPayloadTest.java # Contract tests for ContentPayload
├── skill/
│ ├── TrendFetcherSkillTest.java # Behavioral goalposts for trend fetching
│ └── ContentGeneratorSkillTest.java # Behavioral goalposts for content generation
└── exception/
└── BudgetExceededExceptionTest.java # Exception contract tests


---

## Prerequisites

- **Java 21** or higher
- **Apache Maven 3.9+**

Verify your setup:

bash
java --version
mvn --version

---

## Getting Started
Clone the repository:

git clone https://github.com/dagmawiii03/Project-Chimera-The-Agentic-Infrastructure-Challenge
cd project-chimera

## Build the project:

mvn clean compile


## Run all tests:

mvn clean test


## TDD Approach

This project uses the classic Red-Green-Refactor cycle a deliberate two-tier testing strategy that separates contract tests from behavioral tests.

Contract tests verify that the foundational data structures and interfaces are correctly defined. They check that records validate their inputs, reject nulls, enforce value constraints, and maintain immutability. These tests pass right now because the DTOs and exception are fully implemented.

Behavioral tests verify that the skill implementations actually perform meaningful work. Each behavioral test is tagged with a TDD-FAIL prefix in its display name to clearly signal that it is an intentional goalpost — a requirement written before the implementation exists. These tests fail against the stub implementations because stubs return placeholder data that does not meet the behavioral requirements.

The project is currently in the Red phase: tests are written, they define the target behavior, and they fail. The next phase is Green: build the real implementations (TrendFetcherSkillImpl, ContentGeneratorSkillImpl) that satisfy all behavioral tests. The final phase is Refactor: optimize the passing code.

## Running Tests

mvn clean test


## Expected Test Results

Tests run: 16, Failures: 6, Errors: 0


 ## Demo Guide

 **Step 1** — Clone and Open
Clone the repo and open the project folder in your IDE (VS Code, IntelliJ, etc.).

git clone https://github.com/dagmawiii03/Project-Chimera-The-Agentic-Infrastructure-Challenge

 **Step 2** — Look Around : Open a few files to get familiar with the code:

`TrendData.java` — a record that validates its own fields

`TrendFetcherSkill.java` — the interface that defines what a trend fetcher must do

`TrendFetcherSkillStub.java` — the placeholder that returns fake data

`TrendFetcherSkillTest.java` — the tests, including ones the stub which meant to fail

 **Step 3** — Run the Tests

`mvn clean test`

You should see:
`Tests run: 16`, `Failures: 6`, `Errors: 0`

10 pass. 6 fail on purpose. Zero errors means everything compiles and runs fine.

**Step 4** — Ask the IDE Agent a Question
If your IDE has an AI assistant (Copilot, Cursor, Cline, etc.), ask it something about the project. For example:

`"What Java version does this project use?"`

`"What fields does TrendData have?"`

`"What exception does this project define?"`

It should answer using your actual code, not generic knowledge.

*N.B* : In **VS Code** with Copilot, type @workspace before your question to point it at the project.

## Acknowledgements

- Built as part of a challange five by **10 academy**
