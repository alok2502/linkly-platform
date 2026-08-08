FROM golang:1.23 AS builder
WORKDIR /app
COPY go.mod .
COPY main.go .
RUN CGO_ENABLED=0 go build -o server main.go

FROM gcr.io/distroless/static-debian12
COPY --from=builder /app/server /server
USER nonroot:nonroot
EXPOSE 8080
CMD ["/server"]
