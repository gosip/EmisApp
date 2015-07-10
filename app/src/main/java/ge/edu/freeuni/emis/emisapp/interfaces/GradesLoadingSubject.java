package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;

public interface GradesLoadingSubject {
    public void registerListener(GradesLoadingListener listener);
    public void unRegisterListener(GradesLoadingListener listener);
    public void notifySemesterListDownloaded(List<Semester> semesterList);
}
