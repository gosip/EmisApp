package ge.edu.freeuni.emis.emisapp.model;

import android.util.Log;

import java.util.List;

/**
 * Created by giorgi on 7/7/15.
 */
public class Semester {
    private int numSemester;
    private List<Class> classes;

    // public setters
    public void setNumSemester(int numSemester) {
        this.numSemester = numSemester;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Semester semester = (Semester) o;

        if (numSemester != semester.numSemester) return false;
        return !(classes != null ? !classes.equals(semester.classes) : semester.classes != null);

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Semester{");
        sb.append("numSemester=").append(numSemester);
        sb.append(", classes=").append(classes.toString());
        sb.append('}');
        return sb.toString();
    }

    //public getters
    public int getNumSemester() {
        return numSemester;
    }
    public List<Class> getClasses() {
        return classes;
    }
}
