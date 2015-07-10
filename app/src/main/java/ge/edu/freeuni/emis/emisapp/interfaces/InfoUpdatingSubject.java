package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.model.Student;

public interface InfoUpdatingSubject {
    public void registerListener(InfoUpdatingListener listener);
    public void unRegisterListener(InfoUpdatingListener listener);
    public void notifyInfoUpdated(UpdateMessage updateMessage, Student studentInfo, List<Semester> semesterList);
}
