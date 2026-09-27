package OpsLens.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "metric_events")
@Data
public class MetricEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "metricName is required")
    private String metricName;

    @Column(nullable = false)
    @NotNull(message = "value is required")
    private Double value;

    @Column(nullable = false)
    @NotBlank(message = "serviceName is required")
    private String serviceName;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}