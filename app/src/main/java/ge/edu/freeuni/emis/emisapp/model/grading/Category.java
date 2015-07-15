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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (Double.compare(category.categoryScore, categoryScore) != 0) return false;
        return !(categoryIndicator != null ? !categoryIndicator.equals(category.categoryIndicator) : category.categoryIndicator != null);

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Category{");
        sb.append("categoryIndicator='").append(categoryIndicator).append('\'');
        sb.append(", categoryScore=").append(categoryScore);
        sb.append('}');
        return sb.toString();
    }


}
