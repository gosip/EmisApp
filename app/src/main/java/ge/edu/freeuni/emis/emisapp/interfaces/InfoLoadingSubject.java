package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.model.Student;

public interface InfoLoadingSubject {
    public void registerListener(InfoLoadingListener listener);
    public void unRegisterListener(InfoLoadingListener listener);
    public void notifyInfoLoaded(Student studentInfo, List<Semester> semesterList);
}
