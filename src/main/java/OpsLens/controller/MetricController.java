package OpsLens.controller;

import OpsLens.dto.MetricIngestionResponse;
import OpsLens.entity.MetricEvent;
import OpsLens.service.MetricService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MetricIngestionResponse ingestMetric(
            @Valid @RequestBody MetricEvent metricEvent) {
        return metricService.ingestMetric(metricEvent);
    }

    @GetMapping
    public List<MetricEvent> getAllMetrics() {
        return metricService.getAllMetrics();
    }

    @GetMapping("/{serviceName}")
    public List<MetricEvent> getMetricsByServiceName(
            @PathVariable String serviceName) {
        return metricService.getMetricsByServiceName(serviceName);
    }
}