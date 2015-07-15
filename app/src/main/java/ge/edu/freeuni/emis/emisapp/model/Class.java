package ge.edu.freeuni.emis.emisapp.model;

import android.util.Log;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Class aClass = (Class) o;

        if (numCredits != aClass.numCredits) return false;
        if (className != null ? !className.equals(aClass.className) : aClass.className != null)
            return false;
        if (lecturers != null ? !lecturers.equals(aClass.lecturers) : aClass.lecturers != null)
            return false;
        if (studentsGrade != null ? !studentsGrade.equals(aClass.studentsGrade) : aClass.studentsGrade != null)
            return false;
        if (detailedGradeCategories == null && aClass.detailedGradeCategories != null ||
                detailedGradeCategories != null && aClass.detailedGradeCategories == null)
            return false;
        if (detailedGradeCategories.size() != aClass.detailedGradeCategories.size())
            return false;
        for (int i = 0; i < detailedGradeCategories.size(); ++i) {
            Category category = detailedGradeCategories.get(i);
            if (!aClass.detailedGradeCategories.contains(category))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Class{");
        sb.append("className='").append(className).append('\'');
        sb.append(", lecturers=").append(lecturers);
        sb.append(", numCredits=").append(numCredits);
        sb.append(", studentsGrade=").append(studentsGrade.toString());
        sb.append(", detailedGradeCategories=").append(detailedGradeCategories.toString());
        sb.append(", detailedGrades=").append(detailedGrades.toString());
        sb.append('}');
        return sb.toString();
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
