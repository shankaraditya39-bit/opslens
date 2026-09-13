package OpsLens.service;

import OpsLens.detector.AnomalyDetector;
import OpsLens.dto.MetricIngestionResponse;
import OpsLens.entity.MetricEvent;
import OpsLens.repository.MetricEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MetricService {

    private final MetricEventRepository metricEventRepository;
    private final AnomalyDetector anomalyDetector;

    public MetricService(MetricEventRepository metricEventRepository,
                         AnomalyDetector anomalyDetector) {
        this.metricEventRepository = metricEventRepository;
        this.anomalyDetector = anomalyDetector;
    }

    public MetricIngestionResponse ingestMetric(MetricEvent metricEvent) {
        metricEvent.setTimestamp(LocalDateTime.now());
        MetricEvent saved = metricEventRepository.save(metricEvent);
        boolean isAnomaly = anomalyDetector.isAnomaly(saved);
        return new MetricIngestionResponse(saved, isAnomaly);
    }

    public List<MetricEvent> getAllMetrics() {
        return metricEventRepository.findAll();
    }

    public List<MetricEvent> getMetricsByServiceName(String serviceName) {
        return metricEventRepository.findByServiceName(serviceName);
    }
}