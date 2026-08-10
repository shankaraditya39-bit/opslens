package OpsLens.service;

import OpsLens.entity.MetricEvent;
import OpsLens.repository.MetricEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MetricService {

    private final MetricEventRepository repository;

    public MetricService(MetricEventRepository repository) {
        this.repository = repository;
    }

    public MetricEvent ingestMetric(MetricEvent metricEvent) {
        metricEvent.setTimestamp(LocalDateTime.now());
        return repository.save(metricEvent);
    }
}