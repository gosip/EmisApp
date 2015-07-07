package ge.edu.freeuni.emis.emisapp.model.grading;

/**
 * Created by giorgi on 7/7/15.
 */
public class Grade {
    private String gradeIndicator;
    private double score;

    public Grade(String gradeIndicator, double score) {
        this.gradeIndicator = gradeIndicator;
        this.score = score;
    }

    // public getters
    public String getGradeIndicator() {
        return gradeIndicator;
    }
    public double getScore() {
        return score;
    }

    // setter, possible to be used
    public void setScore(double score) {
        this.score = score;
    }
}
