import java.util.LinkedHashMap;

public class DistanceToLoadWeight {
    private LinkedHashMap<Double, Double> distanceToLoadWeight = new LinkedHashMap<>();

    public LinkedHashMap<Double, Double> getDistanceToLoadWeight() {
        return distanceToLoadWeight;
    }

    public void setDistanceToLoadWeight(LinkedHashMap<Double, Double> distanceToLoadWeight) {
        this.distanceToLoadWeight = distanceToLoadWeight;
    }

    public void addDistanceToLoadWeight (Double distance, Double loadWeight) {
        this.distanceToLoadWeight.put(distance, loadWeight);
    }


}
