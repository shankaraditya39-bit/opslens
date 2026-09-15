package OpsLens.controller;

import OpsLens.entity.Anomaly;
import OpsLens.service.AnomalyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anomalies")
public class AnomalyController {

    private final AnomalyService anomalyService;

    public AnomalyController(AnomalyService anomalyService) {
        this.anomalyService = anomalyService;
    }

    @GetMapping
    public List<Anomaly> getAllAnomalies() {
        return anomalyService.getAllAnomalies();
    }

    @GetMapping("/{serviceName}")
    public List<Anomaly> getAnomaliesByServiceName(
            @PathVariable String serviceName) {
        return anomalyService.getAnomaliesByServiceName(serviceName);
    }
}