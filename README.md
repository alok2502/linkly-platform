# Linkly — Polyglot Microservices URL Shortener

A production-style microservices platform demonstrating the full DevOps lifecycle:
containerization (4 languages), CI/CD, Kubernetes orchestration, GitOps, and monitoring.

## Architecture
- **api-gateway** (Go) — entry point / router
- **user-service** (Python/Flask) — user accounts
- **link-service** (Node/Express) — create/resolve short links
- **analytics-service** (Java/Spring Boot) — click tracking
- **PostgreSQL** — shared datastore

## Stack
Docker · Kubernetes · GitHub Actions · ArgoCD (GitOps) · Prometheus/Grafana · GHCR

## Status
🚧 Building — see commit history
