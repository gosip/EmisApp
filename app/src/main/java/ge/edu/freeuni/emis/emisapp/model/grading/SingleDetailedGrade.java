package ge.edu.freeuni.emis.emisapp.model.grading;

/**
 * Created by giorgi on 7/7/15.
 */
public class SingleDetailedGrade {
    private double maxScore;
    private double weight;
    private double actualScore;

    public SingleDetailedGrade(double maxScore, double weight, double actualScore) {
        this.maxScore = maxScore;
        this.weight = weight;
        this.actualScore = actualScore;
    }

    // public getters
    public double getMaxScore() {
        return maxScore;
    }
    public double getWeight() {
        return weight;
    }
    public double getActualScore() {
        return actualScore;
    }
}
