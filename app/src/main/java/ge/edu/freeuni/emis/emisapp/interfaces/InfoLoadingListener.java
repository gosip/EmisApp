package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.model.Student;

public interface InfoLoadingListener {
    public void onInfoLoaded(Student studentInfo, List<Semester> semesterList);
}
