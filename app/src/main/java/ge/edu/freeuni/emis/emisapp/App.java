package ge.edu.freeuni.emis.emisapp;

import android.app.Application;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.interfaces.AppStateListener;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateSubject;
import ge.edu.freeuni.emis.emisapp.interfaces.GradesLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.InfoLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.InfoUpdatingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.PersonalInfoLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.TranscriptLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.UpdateMessage;
import ge.edu.freeuni.emis.emisapp.loaders.Filename;
import ge.edu.freeuni.emis.emisapp.loaders.GradesLoader;
import ge.edu.freeuni.emis.emisapp.loaders.InfoLoader;
import ge.edu.freeuni.emis.emisapp.loaders.InfoUpdater;
import ge.edu.freeuni.emis.emisapp.loaders.PersonalInfoLoader;
import ge.edu.freeuni.emis.emisapp.loaders.TranscriptLoader;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;
import ge.edu.freeuni.emis.emisapp.model.grading.SingleDetailedGrade;
import ge.edu.freeuni.emis.emisapp.ui.fragments.PlaceHolderFrag;

public class App extends Application implements
        AppStateSubject, GradesLoadingListener, InfoLoadingListener,
        InfoUpdatingListener, PersonalInfoLoadingListener, TranscriptLoadingListener {

    private List<AppStateListener> listeners;

    private List<Semester> semesterList;
    private Student student;
    private List<TranscriptRow> transcript;

    private StudentData internalData;

    private boolean networkConnectionAvailable;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initiateInfoLoading();
        if (networkConnectionAvailable)
            initiateLoadingFromWeb();
    }

    private void init() {
        listeners = new ArrayList<>();
        semesterList = new ArrayList<>();
        transcript = new ArrayList<>();
        internalData = null;
        // checking network connections
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        networkConnectionAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void initiateInfoLoading() {
        InfoLoader infoLoader = new InfoLoader(this);
        infoLoader.registerListener(this);
        infoLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    private void initiateLoadingFromWeb() {
        PersonalInfoLoader personalInfoLoader = new PersonalInfoLoader();
        personalInfoLoader.setContext(this);
        personalInfoLoader.registerListener(this);
        personalInfoLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

        GradesLoader gradesLoader = new GradesLoader();
        gradesLoader.setContext(this);
        gradesLoader.registerListener(this);
        gradesLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

        TranscriptLoader transcriptLoader = new TranscriptLoader();
        transcriptLoader.setContext(this);
        transcriptLoader.registerListener(this);
        transcriptLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    private void initiateUpdate() {
        InfoUpdater updater = new InfoUpdater(this, internalData,
                new StudentData(this.student, this.semesterList, this.transcript));
        updater.registerListener(this);
        updater.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Override
    public void onGradesDownloaded(List<Semester> semesterList) {
        this.semesterList = semesterList;
        notifySemesterListUpdated(semesterList);
    }

    @Override
    public void onPersonalInfoDownloaded(Student studentInfo) {
        studentInfo.setProfileImg(BitmapFactory.decodeResource(getResources(),
                R.drawable.profile_avatar_placeholder));
        this.student = studentInfo;
        notifyStudentInfoUpdated(studentInfo);
    }

    @Override
    public void onTranscriptDownloaded(List<TranscriptRow> transcript) {
        this.transcript = transcript;
        notifyTranscriptUpdated(transcript);
        initiateUpdate();
    }

    @Override
    public void onInfoLoaded(StudentData studentData) {
        this.internalData = studentData;
        this.semesterList = studentData.getSemesterList();
        notifySemesterListUpdated(semesterList);
        this.student = studentData.getStudentInfo();
        notifyStudentInfoUpdated(student);
        this.transcript = studentData.getTranscript();
        notifyTranscriptUpdated(transcript);
    }

    @Override
    public void notifyInfoUpdated(UpdateMessage updateMessage) {
        Log.i("TAG", updateMessage.toString());
    }


    // public getters
    public List<Semester> getSemesterList() {
        return semesterList;
    }

    public List<TranscriptRow> getTranscript() {
        return transcript;
    }

    public Student getStudent() {
        return student;
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
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onSemestersListUpdated(semesterList);
        }
    }

    @Override
    public void notifyStudentInfoUpdated(Student student) {
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onStudentInfoUpdated(student);
        }
    }

    @Override
    public void notifyTranscriptUpdated(List<TranscriptRow> transcript) {
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onTranscriptUpdated(transcript);
        }
    }

}
