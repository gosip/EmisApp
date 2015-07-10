package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;

/**
 * Created by User on 7/10/2015.
 */
public interface GradesLoadingListener {
    public void onGradesDownloaded(List<Semester> semesterList);
}
