package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.model.Student;

/**
 * Created by giorgi on 7/7/15.
 */
public interface AppStateListener {
    public void onSemestersListUpdated(List<Semester> semesterList);
    public void onStudentInfoUpdated(Student student);
}
