package ge.edu.freeuni.emis.emisapp.loaders;

import android.os.AsyncTask;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.interfaces.GradesLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.GradesLoadingSubject;
import ge.edu.freeuni.emis.emisapp.model.Semester;

public class GradesLoader extends AsyncTask implements GradesLoadingSubject {

    private GradesLoadingListener listener = null;

    @Override
    protected Object doInBackground(Object[] params) {
        // TODO
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        notifySemesterListDownloaded((List<Semester>) o);
    }

    @Override
    public void registerListener(GradesLoadingListener listener) {
        this.listener = listener;
    }

    @Override
    public void unRegisterListener(GradesLoadingListener listener) {
        this.listener = null;
    }

    @Override
    public void notifySemesterListDownloaded(List<Semester> semesterList) {
        if (listener != null)
            listener.onGradesDownloaded(semesterList);
    }
}
