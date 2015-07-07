package ge.edu.freeuni.emis.emisapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.interfaces.AppStateListener;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateSubject;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;

/**
 * Created by giorgi on 7/7/15.
 */
public class App extends Application implements AppStateListener, AppStateSubject {
    private List<AppStateListener> listeners;
    private List<Semester> semesterList;

    @Override
    public void onCreate() {
        super.onCreate();
        init();

        // for testing only
        mockInit();
    }

    private void init() {
        listeners = new ArrayList<>();
    }

    private void mockInit() {
        final Grade grade = new Grade("A", 91);
        final List<Class> firstSemesterClasses = new ArrayList<Class>() {{
            add(new Class("Programming methodology", "shota", 6, grade));
            add(new Class("Programming paradigms", "shota", 6, grade));
            add(new Class("Programming abstractions", "shota", 6, grade));
        }};
        final List<Class> secondSemesterClasses = new ArrayList<Class>() {{
            add(new Class("OOP", "shota", 6, grade));
            add(new Class("Mobile programming", "shota", 6, grade));
            add(new Class("Linear algebra", "shota", 6, grade));
        }};

        semesterList = new ArrayList<Semester>(){{
            add(new Semester(1, firstSemesterClasses));
            add(new Semester(2, secondSemesterClasses));
        }};

//        notifySemesterListDownloaded(semesterList);
    }

    @Override
    public void onSemestersListDownloaded(List<Semester> semesterList) {

    }

    @Override
    public void registerListener(AppStateListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    @Override
    public void unRegisterListener(AppStateListener listener) {
        if (listeners.contains(listener))
            listeners.remove(listener);
    }

    @Override
    public void notifySemesterListDownloaded(List<Semester> semesterList) {
        for (AppStateListener listener : listeners) {
            listener.onSemestersListDownloaded(semesterList);
        }
    }


    // public getters
    public List<Semester> getSemesterList() {
        return semesterList;
    }
}
