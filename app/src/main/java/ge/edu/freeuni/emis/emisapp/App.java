package ge.edu.freeuni.emis.emisapp;

import android.app.Application;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.interfaces.AppStateListener;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateSubject;
import ge.edu.freeuni.emis.emisapp.interfaces.GradesLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.InfoLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.InfoUpdatingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.PersonalInfoLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.UpdateMessage;
import ge.edu.freeuni.emis.emisapp.loaders.GradesLoader;
import ge.edu.freeuni.emis.emisapp.loaders.PersonalInfoLoader;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;
import ge.edu.freeuni.emis.emisapp.model.grading.SingleDetailedGrade;
import ge.edu.freeuni.emis.emisapp.ui.fragments.PlaceHolderFrag;

/**
 * Created by giorgi on 7/7/15.
 */
public class App extends Application implements
        AppStateSubject, GradesLoadingListener, InfoLoadingListener,
        InfoUpdatingListener, PersonalInfoLoadingListener {
    private List<AppStateListener> listeners;
    private List<Semester> semesterList;
    private Student student;

    @Override
    public void onCreate() {
        super.onCreate();
        init();

        // for testing only
//        mockInit();


        PersonalInfoLoader loader = new PersonalInfoLoader();
        loader.setContext(this);
        loader.registerListener(this);
        loader.execute();

        GradesLoader gradesLoader = new GradesLoader();
        gradesLoader.setContext(this);
        gradesLoader.registerListener(this);
        gradesLoader.execute();
    }

    private void init() {
        listeners = new ArrayList<>();
        semesterList = new ArrayList<>();
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

        final List<String> lecturers = new ArrayList<String>(){{
            add("shota");
            add("bochora");
        }};

        Class currClass;

        final List<Class> firstSemesterClasses = new ArrayList<Class>();
        currClass = new Class();
        currClass.setClassName("Programming methodology");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        firstSemesterClasses.add(currClass);

        currClass = new Class();
        currClass.setClassName("Programming paradigms");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        firstSemesterClasses.add(currClass);

        currClass = new Class();
        currClass.setClassName("Programming abstractions");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        firstSemesterClasses.add(currClass);

        currClass = new Class();
        currClass.setClassName("Object Oriented Design Patterns");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        firstSemesterClasses.add(currClass);

        currClass = new Class();
        currClass.setClassName("Object Oriented Design Patterns And More Stuff");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        firstSemesterClasses.add(currClass);

        final List<Class> secondSemesterClasses = new ArrayList<Class>();

        currClass = new Class();
        currClass.setClassName("OOP");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        secondSemesterClasses.add(currClass);

        currClass = new Class();
        currClass.setClassName("Mobile programming");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        secondSemesterClasses.add(currClass);

        currClass = new Class();
        currClass.setClassName("Linear algebra");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        secondSemesterClasses.add(currClass);


        final List<Class> thirdSemesterClasses = new ArrayList<Class>();
        currClass = new Class();
        currClass.setClassName("Practicum Practicum");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        thirdSemesterClasses.add(currClass);

        currClass = new Class();
        currClass.setClassName("Linear Programming");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        thirdSemesterClasses.add(currClass);

        currClass = new Class();
        currClass.setClassName("Artificial Intelligence");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        thirdSemesterClasses.add(currClass);

        currClass = new Class();
        currClass.setClassName("Networking");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        thirdSemesterClasses.add(currClass);

        currClass = new Class();
        currClass.setClassName("Literature and art");
        currClass.setLecturers(lecturers);
        currClass.setNumCredits(6);
        currClass.setStudentsGrade(grade);
        thirdSemesterClasses.add(currClass);



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

        Semester semester;
        semesterList = new ArrayList<Semester>();
        semester = new Semester();
        semester.setNumSemester(1);
        semester.setClasses(firstSemesterClasses);
        semesterList.add(semester);

        semester = new Semester();
        semester.setNumSemester(2);
        semester.setClasses(secondSemesterClasses);
        semesterList.add(semester);

        semester = new Semester();
        semester.setNumSemester(3);
        semester.setClasses(thirdSemesterClasses);
        semesterList.add(semester);

//        notifySemesterListDownloaded(semesterList);
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
    public void notifySemesterListUpdated(List<Semester> semesterList) {
        for (AppStateListener listener : listeners) {
            listener.onSemestersListUpdated(semesterList);
        }
    }

    @Override
    public void notifyStudentInfoUpdated(Student student) {
        for (AppStateListener listener : listeners) {
            listener.onStudentInfoUpdated(student);
        }
    }

    // public getters
    public List<Semester> getSemesterList() {
        return semesterList;
    }
    public Student getStudent() {
        return student;
    }

    @Override
    public void onGradesDownloaded(List<Semester> semesterList) {
        this.semesterList = semesterList;
        notifySemesterListUpdated(semesterList);
    }

    @Override
    public void onInfoLoaded(Student studentInfo, List<Semester> semesterList) {

    }

    @Override
    public void notifyInfoUpdated(UpdateMessage updateMessage, Student studentInfo, List<Semester> semesterList) {

    }

    @Override
    public void onPersonalInfoDownloaded(Student studentInfo) {
        studentInfo.setProfileImg(BitmapFactory.decodeResource(getResources(),
                R.drawable.profile_avatar_placeholder));
        this.student = studentInfo;
        notifyStudentInfoUpdated(studentInfo);
    }
}
