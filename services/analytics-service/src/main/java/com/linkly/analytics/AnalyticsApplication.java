package com.linkly.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@RestController
public class AnalyticsApplication {
    private final Map<String, Integer> clicks = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsApplication.class, args);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "healthy", "service", "analytics-service");
    }

    @PostMapping("/track/{code}")
    public Map<String, Object> track(@PathVariable String code) {
        clicks.merge(code, 1, Integer::sum);
        return Map.of("code", code, "clicks", clicks.get(code));
    }

    @GetMapping("/stats/{code}")
    public Map<String, Object> stats(@PathVariable String code) {
        return Map.of("code", code, "clicks", clicks.getOrDefault(code, 0));
    }
}
