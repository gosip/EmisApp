package ge.edu.freeuni.emis.emisapp.model.grading;

/**
 * Created by giorgi on 7/7/15.
 */
public class SingleDetailedGrade {
    private int gradeNumber;
    private double score;
    private double maxScore;
    private double weight;
    private double result;

    public SingleDetailedGrade(int gradeNumber, double score, double maxScore, double weight,
                               double result) {
        this.gradeNumber = gradeNumber;
        this.score = score;
        this.maxScore = maxScore;
        this.weight = weight;
        this.result = result;
    }

    // public getters
    public int getGradeNumber() {
        return gradeNumber;
    }
    public double getScore() {
        return score;
    }
    public double getMaxScore() {
        return maxScore;
    }
    public double getWeight() {
        return weight;
    }
    public double getResult() {
        return result;
    }
}
