# OpsLens

Backend system for real-time metric ingestion and anomaly detection.

Services send metrics (CPU usage, response time, error count) via REST API. 
OpsLens stores them and flags anomalies using a sliding window algorithm — 
if a value deviates more than 50% from the last 10 readings, it's flagged.

## Tech Stack
Java 21 · Spring Boot · PostgreSQL · Spring Data JPA · Docker · Gradle

## Running Locally
Requires Java 21 and Docker.

```bash
# Start database
docker run --name opslens-db \
  -e POSTGRES_USER=opslens \
  -e POSTGRES_PASSWORD=opslens123 \
  -e POSTGRES_DB=opslensdb \
  -p 5432:5432 -d postgres:15

# Copy config
cp src/main/resources/application.properties.example \
   src/main/resources/application.properties

# Run
./gradlew bootRun
```

## API
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /health | Health check |
| POST | /metrics | Ingest a metric |
| GET | /metrics | Get all metrics |
| GET | /metrics/{serviceName} | Filter by service |

## Status
In progress — anomaly persistence and AI-driven incident diagnosis coming next.
