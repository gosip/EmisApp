package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.model.Student;

public interface InfoUpdatingListener {
    public void notifyInfoUpdated(UpdateMessage updateMessage, Student studentInfo, List<Semester> semesterList);
}
