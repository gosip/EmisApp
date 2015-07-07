package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;

/**
 * Created by giorgi on 7/7/15.
 */
public interface AppStateListener {
    public void onSemestersListDownloaded(List<Semester> semesterList);
}
