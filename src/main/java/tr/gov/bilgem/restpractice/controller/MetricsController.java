package tr.gov.bilgem.restpractice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.gov.bilgem.restpractice.misc.RequestMetrics;

@RestController
@RequestMapping("${api.root}/metrics")
public class MetricsController {

    private final RequestMetrics metrics;

    @Value("${metrics.enabled:true}")
    private boolean metricsEnabled;

    public MetricsController(RequestMetrics metrics) {
        this.metrics = metrics;
    }

    @GetMapping
    public ResponseEntity<?> getMetrics() {
        if (!metricsEnabled) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(metrics.getSnapshot());
    }
}