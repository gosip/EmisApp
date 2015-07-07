package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;

/**
 * Created by giorgi on 7/7/15.
 */
public interface AppStateSubject {
    public void registerListener(AppStateListener listener);
    public void unRegisterListener(AppStateListener listener);
    public void notifySemesterListDownloaded(List<Semester> semesterList);
}
