# ============================================================
# Stage 1 — BUILD: compile the JAR using Maven + JDK 21
# ============================================================

FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /workspace

# Step 1: copying the pom.xml first so Docker caches dependency resolution.
# Re-downloading dependencies happens only if pom.xml changes
COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline

# Step 2: Copying sources and build. Code change does not affect dependency above
COPY src ./src
RUN mvn -B -ntp clean package -DskipTests

# ============================================================
# Stage 2 — RUNTIME: slim JRE, just enough to run the JAR
# ============================================================

FROM eclipse-temurin:21-jre-alpine

# Create a non-root user and group
RUN addgroup -S app && adduser -S app -G app

WORKDIR /app

# Copying only the built JAR from the build stage
COPY --from=build --chown=app:app /workspace/target/calculator-devops-*.jar /app/app.jar

EXPOSE 8080

# Not running containers as root in production
USER app

# JVM entry point
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
