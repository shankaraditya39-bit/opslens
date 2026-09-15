package OpsLens.repository;

import OpsLens.entity.Anomaly;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnomalyRepository extends JpaRepository<Anomaly, Long> {

    List<Anomaly> findByServiceName(String serviceName);
}