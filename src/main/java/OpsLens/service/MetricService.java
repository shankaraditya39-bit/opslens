package OpsLens.service;

import OpsLens.detector.AnomalyDetector;
import OpsLens.dto.AnomalyResult;
import OpsLens.dto.MetricIngestionResponse;
import OpsLens.entity.Anomaly;
import OpsLens.entity.MetricEvent;
import OpsLens.repository.AnomalyRepository;
import OpsLens.repository.MetricEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MetricService {

    private final MetricEventRepository metricEventRepository;
    private final AnomalyDetector anomalyDetector;
    private final AnomalyRepository anomalyRepository;
    private final AiDiagnosisService aiDiagnosisService;

    public MetricService(
            MetricEventRepository metricEventRepository,
            AnomalyDetector anomalyDetector,
            AnomalyRepository anomalyRepository,
            AiDiagnosisService aiDiagnosisService) {

        this.metricEventRepository = metricEventRepository;
        this.anomalyDetector = anomalyDetector;
        this.anomalyRepository = anomalyRepository;
        this.aiDiagnosisService = aiDiagnosisService;
    }

    // POST /metrics
    public MetricIngestionResponse ingestMetric(MetricEvent metricEvent) {

        metricEvent.setTimestamp(LocalDateTime.now());

        // Check for anomaly
        AnomalyResult anomalyResult =
                anomalyDetector.isAnomaly(metricEvent);

        // If anomaly detected
        if (anomalyResult.isAnomaly()) {

            // Ask Gemini AI for diagnosis
            String diagnosis = aiDiagnosisService.getDiagnosis(
                    metricEvent.getServiceName(),
                    metricEvent.getMetricName(),
                    metricEvent.getValue(),
                    anomalyResult.getAverage()
            );

            // Create anomaly record
            Anomaly anomaly = new Anomaly();

            anomaly.setServiceName(metricEvent.getServiceName());
            anomaly.setMetricName(metricEvent.getMetricName());
            anomaly.setAnomalousValue(metricEvent.getValue());
            anomaly.setAverageValue(anomalyResult.getAverage());
            anomaly.setDetectedAt(LocalDateTime.now());
            anomaly.setDiagnosis(diagnosis);

            // Save anomaly
            anomalyRepository.save(anomaly);
        }

        // Save metric
        MetricEvent savedMetric =
                metricEventRepository.save(metricEvent);

        return new MetricIngestionResponse(
                savedMetric,
                anomalyResult.isAnomaly()
        );
    }

    // GET /metrics
    public List<MetricEvent> getAllMetrics() {
        return metricEventRepository.findAll();
    }

    // GET /metrics/{serviceName}
    public List<MetricEvent> getMetricsByServiceName(String serviceName) {
        return metricEventRepository.findByServiceName(serviceName);
    }
}