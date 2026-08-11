package OpsLens.detector;

import OpsLens.entity.MetricEvent;
import OpsLens.repository.MetricEventRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnomalyDetector {

    private final MetricEventRepository metricEventRepository;

    public AnomalyDetector(MetricEventRepository metricEventRepository) {
        this.metricEventRepository = metricEventRepository;
    }

    public boolean isAnomaly(MetricEvent metricEvent) {

        List<MetricEvent> recentMetrics =
                metricEventRepository.findTop10ByServiceNameAndMetricNameOrderByTimestampDesc(
                        metricEvent.getServiceName(),
                        metricEvent.getMetricName()
                );

        // Not enough data to establish a baseline
        if (recentMetrics.size() < 3) {
            return false;
        }

        // Calculate average of the recent metrics
        double average = recentMetrics.stream()
                .mapToDouble(MetricEvent::getValue)
                .average()
                .orElse(0.0);

        // Handle zero-average case
        if (average == 0.0) {
            return metricEvent.getValue() != 0.0;
        }

        // Calculate percentage deviation from the average
        double deviation =
                Math.abs(metricEvent.getValue() - average) / average;

        // More than 50% deviation = anomaly
        return deviation > 0.50;
    }
}