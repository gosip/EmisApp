package ge.edu.freeuni.emis.emisapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ge.edu.freeuni.emis.emisapp.model.grading.Category;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;
import ge.edu.freeuni.emis.emisapp.model.grading.SingleDetailedGrade;

/**
 * Created by giorgi on 7/7/15.
 */
public class Class {
    private String className;
    private List<String> lecturers;
    private int numCredits;
    private Grade studentsGrade;

    // list of categories, like homework or quizzes
    private List<Category> detailedGradeCategories;
    private Map<String, List<SingleDetailedGrade>> detailedGrades;

    public Class() {
        this.detailedGradeCategories = new ArrayList<>();
        this.detailedGrades = new HashMap<>();
    }

    public void addDetailedGrade(String category, SingleDetailedGrade detailedGrade,
                                 double categoryScore) {
        if (!detailedGrades.containsKey(category)) {
            detailedGradeCategories.add(new Category(category, categoryScore));
            detailedGrades.put(category, new ArrayList<SingleDetailedGrade>());
        }
        detailedGrades.get(category).add(detailedGrade);
    }

    // public getters
    public String getClassName() {
        return className;
    }
    public List<String> getLecturers() {
        return lecturers;
    }
    public int getNumCredits() {
        return numCredits;
    }
    public Grade getStudentsGrade() {
        return studentsGrade;
    }
    public List<Category> getDetailedGradeCategories() {
        return detailedGradeCategories;
    }
    public Map<String, List<SingleDetailedGrade>> getDetailedGrades() {
        return detailedGrades;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    public void setLecturers(List<String> lecturers) {
        this.lecturers = lecturers;
    }
    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }
    public void setStudentsGrade(Grade studentsGrade) {
        this.studentsGrade = studentsGrade;
    }
}
