package ge.edu.freeuni.emis.emisapp.model;

import java.util.List;

/**
 * Created by giorgi on 7/7/15.
 */
public class Semester {
    private int numSemester;
    private List<Class> classes;

    public Semester(int numSemester, List<Class> classes) {
        this.numSemester = numSemester;
        this.classes = classes;
    }

    //public getters
    public int getNumSemester() {
        return numSemester;
    }
    public List<Class> getClasses() {
        return classes;
    }
}
