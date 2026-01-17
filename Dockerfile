# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn -q -e -B -DskipTests dependency:go-offline || true
COPY src ./src
RUN mvn -q -e -B -DskipTests package

# ---- Runtime Stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Create mount points for persistence in compose
VOLUME ["/data", "/cron", "/app/keys"]

# Copy artifact
COPY --from=builder /build/target/*-SNAPSHOT.jar /app/app.jar

ENV SERVER_PORT=8080
EXPOSE 8080

# Install curl for healthcheck
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl ca-certificates \
    && rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["java","-jar","/app/app.jar"]
