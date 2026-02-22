# =============================================================================
# Project Chimera — Multi-Stage Docker Build
# =============================================================================

# ---------- Stage 1: Build ----------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom first (caches dependency layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and config
COPY src ./src
COPY checkstyle.xml .

# Build and run checks
RUN mvn clean package -DskipTests

# ---------- Stage 2: Test (optional, used by make docker-test) ----------
FROM build AS test
RUN mvn test

# ---------- Stage 3: Runtime ----------
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Non-root user for security
RUN groupadd -r chimera && useradd -r -g chimera chimera
USER chimera

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]