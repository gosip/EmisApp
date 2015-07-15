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

    public SingleDetailedGrade(int gradeNumber, double score, double maxScore,
                               double weight, double result) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleDetailedGrade that = (SingleDetailedGrade) o;

        if (gradeNumber != that.gradeNumber) return false;
        if (Double.compare(that.score, score) != 0) return false;
        if (Double.compare(that.maxScore, maxScore) != 0) return false;
        if (Double.compare(that.weight, weight) != 0) return false;
        return Double.compare(that.result, result) == 0;

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SingleDetailedGrade{");
        sb.append("gradeNumber=").append(gradeNumber);
        sb.append(", score=").append(score);
        sb.append(", maxScore=").append(maxScore);
        sb.append(", weight=").append(weight);
        sb.append(", result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
