package ge.edu.freeuni.emis.emisapp.model;

import ge.edu.freeuni.emis.emisapp.model.grading.Grade;

/**
 * Created by giorgi on 7/7/15.
 */
public class Class {
    private String className;
    private String lecturer;
    private int numCredits;
    private Grade studentsGrade;

    public Class(String className, String lecturer, int numCredits, Grade studentsGrade) {
        this.className = className;
        this.lecturer = lecturer;
        this.numCredits = numCredits;
        this.studentsGrade = studentsGrade;
    }

    // public getters
    public String getClassName() {
        return className;
    }
    public String getLecturer() {
        return lecturer;
    }
    public int getNumCredits() {
        return numCredits;
    }
    public Grade getStudentsGrade() {
        return studentsGrade;
    }
}
