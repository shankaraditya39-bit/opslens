package OpsLens.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "metric_events")
@Data
public class MetricEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String metricName;

    @Column
    private Double value;

    @Column
    private String serviceName;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}