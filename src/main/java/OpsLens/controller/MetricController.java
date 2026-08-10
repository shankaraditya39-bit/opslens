package OpsLens.controller;

import OpsLens.entity.MetricEvent;
import OpsLens.service.MetricService;
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
    public MetricEvent ingestMetric(@RequestBody MetricEvent metricEvent) {
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