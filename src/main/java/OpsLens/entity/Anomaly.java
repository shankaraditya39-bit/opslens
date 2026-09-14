package OpsLens.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "anomalies")
@Data
public class Anomaly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String metricName;

    @Column(nullable = false)
    private Double anomalousValue;

    @Column(nullable = false)
    private Double averageValue;

    @Column(nullable = false)
    private LocalDateTime detectedAt;
}