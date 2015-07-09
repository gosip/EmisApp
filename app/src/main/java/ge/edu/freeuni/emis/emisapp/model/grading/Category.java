package ge.edu.freeuni.emis.emisapp.model.grading;

/**
 * Created by giorgi on 7/9/15.
 */
public class Category {
    private String categoryIndicator;
    private double categoryScore;

    public Category(String categoryIndicator, double categoryScore) {
        this.categoryIndicator = categoryIndicator;
        this.categoryScore = categoryScore;
    }

    // public getters
    public String getCategoryIndicator() {
        return categoryIndicator;
    }
    public double getCategoryScore() {
        return categoryScore;
    }
}
