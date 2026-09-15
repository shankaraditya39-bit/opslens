
package OpsLens.service;

import OpsLens.entity.Anomaly;
import OpsLens.repository.AnomalyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnomalyService {

    private final AnomalyRepository anomalyRepository;

    public AnomalyService(AnomalyRepository anomalyRepository) {
        this.anomalyRepository = anomalyRepository;
    }

    public List<Anomaly> getAllAnomalies() {
        return anomalyRepository.findAll();
    }

    public List<Anomaly> getAnomaliesByServiceName(String serviceName) {
        return anomalyRepository.findByServiceName(serviceName);
    }
}