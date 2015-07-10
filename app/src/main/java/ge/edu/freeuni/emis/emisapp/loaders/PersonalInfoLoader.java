package ge.edu.freeuni.emis.emisapp.loaders;

import android.os.AsyncTask;

import ge.edu.freeuni.emis.emisapp.interfaces.PersonalInfoLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.PersonalInfoLoadingSubject;
import ge.edu.freeuni.emis.emisapp.model.Student;

public class PersonalInfoLoader extends AsyncTask
        implements PersonalInfoLoadingSubject{

    private PersonalInfoLoadingListener listener = null;

    @Override
    protected Object doInBackground(Object[] params) {
        // TODO
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        notifyPersonalInfoDownloaded((Student)o);
    }

    @Override
    public void registerListener(PersonalInfoLoadingListener listener) {
        this.listener = listener;
    }

    @Override
    public void unRegisterListener(PersonalInfoLoadingListener listener) {
        this.listener = null;
    }

    @Override
    public void notifyPersonalInfoDownloaded(Student studentInfo) {
        if (this.listener != null)
           this.listener.onPersonalInfoDownloaded(studentInfo);
    }


}
