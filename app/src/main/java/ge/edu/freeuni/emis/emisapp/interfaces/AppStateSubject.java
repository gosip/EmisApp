package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.model.Student;

/**
 * Created by giorgi on 7/7/15.
 */
public interface AppStateSubject {
    public void registerListener(AppStateListener listener);
    public void unRegisterListener(AppStateListener listener);
    public void notifySemesterListUpdated(List<Semester> semesterList);
    public void notifyStudentInfoUpdated(Student student);
}
