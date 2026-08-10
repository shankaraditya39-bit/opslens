package OpsLens.service;

import OpsLens.entity.MetricEvent;
import OpsLens.repository.MetricEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MetricService {

    private final MetricEventRepository metricEventRepository;

    public MetricService(MetricEventRepository metricEventRepository) {
        this.metricEventRepository = metricEventRepository;
    }

    public MetricEvent ingestMetric(MetricEvent metricEvent) {
        metricEvent.setTimestamp(LocalDateTime.now());
        return metricEventRepository.save(metricEvent);
    }

    public List<MetricEvent> getAllMetrics() {
        return metricEventRepository.findAll();
    }

    public List<MetricEvent> getMetricsByServiceName(String serviceName) {
        return metricEventRepository.findByServiceName(serviceName);
    }
}