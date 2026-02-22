makefile
# =============================================================================
# Project Chimera — Build Automation
# =============================================================================
# Wraps Maven commands for standardized project operations.
# Usage: make <target>
# =============================================================================

.PHONY: setup test lint clean spec-check docker-test help

# Default target
help:
	@echo "Project Chimera — Available Targets"
	@echo "===================================="
	@echo "  make setup        Resolve dependencies, compile (skip tests)"
	@echo "  make test         Run JUnit 5 tests (expect TDD failures)"
	@echo "  make lint         Run Checkstyle code quality checks"
	@echo "  make clean        Clean build artifacts"
	@echo "  make spec-check   Verify code aligns with specs (bonus)"
	@echo "  make docker-test  Run tests inside Docker (bonus)"
	@echo "  make all          Run setup + lint + test"

# -----------------------------------------------------------------------------
# Core Targets
# -----------------------------------------------------------------------------

## Resolve dependencies and compile (skip tests for fast setup)
setup:
	mvn clean install -DskipTests

## Run all JUnit 5 tests via Maven
test:
	mvn test

## Run Checkstyle for code quality enforcement
lint:
	mvn checkstyle:check

## Clean all build artifacts
clean:
	mvn clean

## Full pipeline: setup, lint, then test
all: setup lint test

# -----------------------------------------------------------------------------
# Bonus Targets
# -----------------------------------------------------------------------------

## Verify code-to-spec alignment (checks that key spec-defined classes exist)
spec-check:
	@echo "=== Spec Alignment Check ==="
	@echo "Checking spec-defined Records exist..."
	@test -f src/main/java/com/tenx/enterprise/dto/TrendData.java \
		&& echo "[PASS] TrendData.java exists" \
		|| echo "[FAIL] TrendData.java missing"
	@test -f src/main/java/com/tenx/enterprise/dto/TaskEnvelope.java \
		&& echo "[PASS] TaskEnvelope.java exists" \
		|| echo "[FAIL] TaskEnvelope.java missing"
	@echo "Checking skill interfaces exist..."
	@test -f src/main/java/com/tenx/enterprise/skill/TrendFetcherSkill.java \
		&& echo "[PASS] TrendFetcherSkill.java exists" \
		|| echo "[FAIL] TrendFetcherSkill.java missing"
	@test -f src/main/java/com/tenx/enterprise/skill/ContentGeneratorSkill.java \
		&& echo "[PASS] ContentGeneratorSkill.java exists" \
		|| echo "[FAIL] ContentGeneratorSkill.java missing"
	@echo "Checking test files exist..."
	@test -f src/test/java/com/tenx/enterprise/TrendFetcherTest.java \
		&& echo "[PASS] TrendFetcherTest.java exists" \
		|| echo "[FAIL] TrendFetcherTest.java missing"
	@test -f src/test/java/com/tenx/enterprise/SkillsInterfaceTest.java \
		&& echo "[PASS] SkillsInterfaceTest.java exists" \
		|| echo "[FAIL] SkillsInterfaceTest.java missing"
	@echo "Checking specs exist..."
	@test -f specs/_meta.md && echo "[PASS] specs/_meta.md exists" \
		|| echo "[FAIL] specs/_meta.md missing"
	@test -f specs/functional.md && echo "[PASS] specs/functional.md exists" \
		|| echo "[FAIL] specs/functional.md missing"
	@test -f specs/technical.md && echo "[PASS] specs/technical.md exists" \
		|| echo "[FAIL] specs/technical.md missing"
	@echo "=== Spec Check Complete ==="

## Run tests inside Docker container (requires Docker installed)
docker-test:
	docker build -t chimera-test .
	docker run --rm chimera-test mvn test