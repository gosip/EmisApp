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
}
