# Linkly — Polyglot Microservices Platform

A production-style microservices URL shortener demonstrating the **full DevOps lifecycle**:
multi-language containerization, automated CI/CD, Kubernetes orchestration, GitOps, and monitoring.

## Architecture
| Service | Language | Role | Container |
|---|---|---|---|
| api-gateway | Go | Entry/router | multi-stage → distroless (~18MB) |
| user-service | Python/Flask | User accounts | slim + pip caching |
| link-service | Node/Express | Shorten/resolve links | alpine + npm caching |
| analytics-service | Java/Spring Boot | Click tracking | multi-stage Maven→JRE |
| postgres | PostgreSQL | Datastore | official + PVC |

## DevOps Pipeline
git push
→ GitHub Actions CI (matrix build → Trivy scan → push 4 images to GHCR)
→ Kubernetes (Deployments, Services, ConfigMaps, Secrets, PVC, probes, HA replicas)
→ ArgoCD GitOps (auto-sync + self-heal from this repo)
→ Prometheus/Grafana (monitoring)
## Key implementation details
- **4 Dockerfile patterns** — compiled (Go static→distroless) vs interpreted (Python/Node slim)
  vs JVM (Java multi-stage build→JRE). Dependency-layer caching in all. Non-root everywhere.
- **CI** — matrix strategy builds all 4 services in parallel; Trivy security gate (ignore-unfixed);
  images tagged with commit SHA + latest; GHCR via GITHUB_TOKEN (no hardcoded creds).
- **Kubernetes** — readiness/liveness probes, resource requests/limits (JVM gets more),
  2-replica HA for stateless services, PVC-backed Postgres, service discovery via cluster DNS.
- **GitOps** — ArgoCD watches this repo; automated sync + self-heal (manual drift auto-reverted).
- **Monitoring** — kube-prometheus-stack; per-namespace resource dashboards + alerting.

## Run locally
`docker compose up --build`

## Deploy to Kubernetes
`kubectl apply -f k8s/`  (or let ArgoCD sync it)
