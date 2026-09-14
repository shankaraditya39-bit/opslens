package OpsLens.service;

import java.time.LocalDateTime;
import java.util.List;

import OpsLens.detector.AnomalyDetector;
import OpsLens.dto.AnomalyResult;
import OpsLens.dto.MetricIngestionResponse;
import OpsLens.entity.Anomaly;
import OpsLens.entity.MetricEvent;
import OpsLens.repository.AnomalyRepository;
import OpsLens.repository.MetricEventRepository;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

    private final MetricEventRepository metricEventRepository;
    private final AnomalyDetector anomalyDetector;
    private final AnomalyRepository anomalyRepository;

    public MetricService(
            MetricEventRepository metricEventRepository,
            AnomalyDetector anomalyDetector,
            AnomalyRepository anomalyRepository) {

        this.metricEventRepository = metricEventRepository;
        this.anomalyDetector = anomalyDetector;
        this.anomalyRepository = anomalyRepository;
    }

    public MetricIngestionResponse ingestMetric(MetricEvent metricEvent) {

        metricEvent.setTimestamp(LocalDateTime.now());

        // Check for anomaly BEFORE saving the metric
        AnomalyResult anomalyResult =
                anomalyDetector.isAnomaly(metricEvent);

        // If anomaly detected, save it to anomalies table
        if (anomalyResult.isAnomaly()) {

            Anomaly anomaly = new Anomaly();

            anomaly.setServiceName(metricEvent.getServiceName());
            anomaly.setMetricName(metricEvent.getMetricName());
            anomaly.setAnomalousValue(metricEvent.getValue());
            anomaly.setAverageValue(anomalyResult.getAverage());
            anomaly.setDetectedAt(LocalDateTime.now());

            anomalyRepository.save(anomaly);
        }

        // Save the metric event
        MetricEvent saved =
                metricEventRepository.save(metricEvent);

        // Return API response
        return new MetricIngestionResponse(
                saved,
                anomalyResult.isAnomaly()
        );
    }

    // Get all metrics
    public List<MetricEvent> getAllMetrics() {
        return metricEventRepository.findAll();
    }

    // Get metrics by service name
    public List<MetricEvent> getMetricsByServiceName(String serviceName) {
        return metricEventRepository.findByServiceName(serviceName);
    }
}