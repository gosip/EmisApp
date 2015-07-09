package ge.edu.freeuni.emis.emisapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.interfaces.AppStateListener;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateSubject;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;
import ge.edu.freeuni.emis.emisapp.model.grading.SingleDetailedGrade;

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
        final SingleDetailedGrade[] homework = new SingleDetailedGrade[] {
                new SingleDetailedGrade(1, 100, 100, 20, 20),
                new SingleDetailedGrade(2, 100, 100, 20, 20),
                new SingleDetailedGrade(3, 100, 100, 20, 20),
                new SingleDetailedGrade(4, 100, 100, 20, 20),
                new SingleDetailedGrade(5, 100, 100, 20, 20),
                new SingleDetailedGrade(6, 100, 100, 20, 20),
        };
        final SingleDetailedGrade[] quizzes = new SingleDetailedGrade[] {
                new SingleDetailedGrade(1, 100, 50, 30, 15),
                new SingleDetailedGrade(2, 100, 50, 30, 15),
                new SingleDetailedGrade(3, 100, 50, 30, 15),
                new SingleDetailedGrade(4, 100, 50, 30, 15),
                new SingleDetailedGrade(5, 100, 50, 30, 15),
        };
        final SingleDetailedGrade[] midterms = new SingleDetailedGrade[] {
                new SingleDetailedGrade(1, 100, 75, 40, 30),
                new SingleDetailedGrade(2, 100, 70, 40, 28),
        };

        final List<Class> firstSemesterClasses = new ArrayList<Class>() {{
            add(new Class("Programming methodology", "shota", 6, grade));
            add(new Class("Programming paradigms", "shota", 6, grade));
            add(new Class("Programming abstractions", "shota", 6, grade));
            add(new Class("Object Oriented Design Patterns", "shota", 6, grade));
            add(new Class("Object Oriented Design Patterns And More Stuff", "shota", 6, grade));
        }};
        final List<Class> secondSemesterClasses = new ArrayList<Class>() {{
            add(new Class("OOP", "shota", 6, grade));
            add(new Class("Mobile programming", "shota", 6, grade));
            add(new Class("Linear algebra", "shota", 6, grade));
        }};
        final List<Class> thirdSemesterClasses = new ArrayList<Class>() {{
            add(new Class("Practicum Practicum", "shota", 6, grade));
            add(new Class("Linear Programming", "shota", 6, grade));
            add(new Class("Artificial Intelligence", "shota", 6, grade));
            add(new Class("Networking", "shota", 6, grade));
            add(new Class("Literature and art", "shota", 6, grade));
        }};

        for (Class firstSemesterClass : firstSemesterClasses) {
            for (SingleDetailedGrade singleDetailedGrade : homework) {
                firstSemesterClass.addDetailedGrade("Homework", singleDetailedGrade, 30.4);
            }
            for (SingleDetailedGrade quizze : quizzes) {
                firstSemesterClass.addDetailedGrade("Quizzes", quizze, 10.5);
            }
            for (SingleDetailedGrade midterm : midterms) {
                firstSemesterClass.addDetailedGrade("Midterms", midterm, 12.5);
            }
        };

        for (Class firstSemesterClass : secondSemesterClasses) {
            for (SingleDetailedGrade singleDetailedGrade : homework) {
                firstSemesterClass.addDetailedGrade("Homework", singleDetailedGrade, 40.5);
            }
            for (SingleDetailedGrade quizze : quizzes) {
                firstSemesterClass.addDetailedGrade("Quizzes", quizze, 22.4);
            }
            for (SingleDetailedGrade midterm : midterms) {
                firstSemesterClass.addDetailedGrade("Midterms", midterm, 10.2);
            }
        };

        for (Class firstSemesterClass : thirdSemesterClasses) {
            for (SingleDetailedGrade singleDetailedGrade : homework) {
                firstSemesterClass.addDetailedGrade("Homework", singleDetailedGrade, 64.4);
            }
            for (SingleDetailedGrade quizze : quizzes) {
                firstSemesterClass.addDetailedGrade("Quizzes", quizze, 15.7);
            }
            for (SingleDetailedGrade midterm : midterms) {
                firstSemesterClass.addDetailedGrade("Midterms", midterm, 56);
            }
        };

        semesterList = new ArrayList<Semester>(){{
            add(new Semester(1, firstSemesterClasses));
            add(new Semester(2, secondSemesterClasses));
            add(new Semester(3, thirdSemesterClasses));
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
