package ge.edu.freeuni.emis.emisapp.model;

/**
 * Created by giorgi on 7/11/15.
 */
public class TranscriptRow extends Class {
    private String classCode;
    private String semesterName;
    private int creditsEarned;
    private double scoreEarned;

    public TranscriptRow() {
        super();
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }
    public void setCreditsEarned(int creditsEarned) {
        this.creditsEarned = creditsEarned;
    }
    public void setScoreEarned(double scoreEarned) {
        this.scoreEarned = scoreEarned;
    }

    // getters
    public String getClassCode() {
        return classCode;
    }
    public String getSemesterName() {
        return semesterName;
    }
    public int getCreditsEarned() {
        return creditsEarned;
    }
    public double getScoreEarned() {
        return scoreEarned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TranscriptRow that = (TranscriptRow) o;

        if (creditsEarned != that.creditsEarned) return false;
        if (Double.compare(that.scoreEarned, scoreEarned) != 0) return false;
        if (classCode != null ? !classCode.equals(that.classCode) : that.classCode != null)
            return false;
        if (!getStudentsGrade().equals(that.getStudentsGrade()))
            return false;
        if (!getClassName().equals(that.getClassName()))
            return false;
        return !(semesterName != null ? !semesterName.equals(that.semesterName) : that.semesterName != null);

    }

    @Override
    public String toString() {
        return "TranscriptRow{" +
                "classCode='" + classCode + '\'' +
                ", semesterName='" + semesterName + '\'' +
                ", creditsEarned=" + creditsEarned +
                ", scoreEarned=" + scoreEarned +
                '}';
    }
}
