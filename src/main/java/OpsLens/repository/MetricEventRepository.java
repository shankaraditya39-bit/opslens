package OpsLens.repository;

import OpsLens.entity.MetricEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetricEventRepository extends JpaRepository<MetricEvent, Long> {

    List<MetricEvent> findByServiceName(String serviceName);
}