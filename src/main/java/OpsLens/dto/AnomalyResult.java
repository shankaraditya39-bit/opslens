package OpsLens.dto;

public class AnomalyResult {

    private boolean anomaly;
    private double average;

    public AnomalyResult(boolean anomaly, double average) {
        this.anomaly = anomaly;
        this.average = average;
    }

    public boolean isAnomaly() {
        return anomaly;
    }

    public double getAverage() {
        return average;
    }
}