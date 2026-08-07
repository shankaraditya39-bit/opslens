package OpsLens.repository;

import OpsLens.entity.MetricEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricEventRepository extends JpaRepository<MetricEvent, Long> {
}