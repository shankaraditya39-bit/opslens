package OpsLens.dto;

import OpsLens.entity.MetricEvent;
import lombok.Data;

@Data
public class MetricIngestionResponse {

    private MetricEvent metricEvent;
    private boolean anomalyDetected;

    public MetricIngestionResponse(MetricEvent metricEvent, boolean anomalyDetected) {
        this.metricEvent = metricEvent;
        this.anomalyDetected = anomalyDetected;
    }
}