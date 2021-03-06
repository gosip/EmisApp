package ge.edu.freeuni.emis.emisapp;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.background.AlarmReceiver;
import ge.edu.freeuni.emis.emisapp.background.RefreshService;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateListener;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateSubject;
import ge.edu.freeuni.emis.emisapp.interfaces.GradesLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.InfoLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.InfoUpdatingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.PersonalInfoLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.TranscriptLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.UpdateMessage;
import ge.edu.freeuni.emis.emisapp.loaders.GradesLoader;
import ge.edu.freeuni.emis.emisapp.loaders.InfoLoader;
import ge.edu.freeuni.emis.emisapp.loaders.InfoUpdater;
import ge.edu.freeuni.emis.emisapp.loaders.PersonalInfoLoader;
import ge.edu.freeuni.emis.emisapp.loaders.TranscriptLoader;
import ge.edu.freeuni.emis.emisapp.model.*;

public class App extends OnOffApplication implements
        AppStateSubject, GradesLoadingListener, InfoLoadingListener,
        InfoUpdatingListener, PersonalInfoLoadingListener, TranscriptLoadingListener {

    public static final boolean DEFAULT_NOTIF = true;
    public static final int DEFAULT_REFRESH_PERIOD = 5;
    public static final boolean DEFAULT_REFRESH_ON = true;
    public static final int DEFAULT_PAUSE_MS = 5 * 60 * 1000;

    private List<AppStateListener> listeners;

    private List<Semester> semesterList;
    private Student student;
    private List<TranscriptRow> transcript;
    private StudentData internalData;

    private SharedPreferences preferences;
    private UserPreferences userPreferences;

    private boolean networkConnectionAvailable;

    private static AlarmManager am = null;
    private static PendingIntent pIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initUserPreferences();
        Log.i("TAG", userPreferences.toString());
        if (userPreferences.isRefreshOn())
            setAlarm(this, userPreferences.getRefreshPeriod());
        initiateInfoLoading();
        if (networkConnectionAvailable)
            initiateLoadingFromWeb();
    }

    public static void setAlarm(Context context, int intervalMins) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + DEFAULT_PAUSE_MS,
                intervalMins * 60 * 1000, pIntent);
        Log.i("TAG", "print alarm " + intervalMins * 60000);
    }

    public void turnAlarmOff() {
        if (am != null) {
            am.cancel(pIntent);
        }
    }

    private void initUserPreferences() {
        this.userPreferences = new UserPreferences();
        userPreferences.setRefreshOn(preferences.getBoolean(getString(R.string.refresh_on),
                DEFAULT_REFRESH_ON));
        userPreferences.setNotificationsOn(preferences.getBoolean(getString(R.string.notification_pref),
                DEFAULT_NOTIF));
        userPreferences.setRefreshPeriod(preferences.getInt(getString(R.string.refresh_period),
                DEFAULT_REFRESH_PERIOD));
        if (!preferences.contains(getString(R.string.refresh_period))) {
            storePreferences();
        }
    }

    public void storePreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(getString(R.string.notification_pref), userPreferences.isNotificationsOn());
        String refreshOn = getString(R.string.refresh_on);
        if (preferences.getBoolean(refreshOn, DEFAULT_REFRESH_ON) != userPreferences.isRefreshOn()) {
            if (userPreferences.isRefreshOn())
                setAlarm(this, userPreferences.getRefreshPeriod());
            else
                turnAlarmOff();
            editor.putBoolean(refreshOn, userPreferences.isRefreshOn());
        }
        String refreshPeriod = getString(R.string.refresh_period);
        if (preferences.getInt(refreshPeriod, DEFAULT_REFRESH_PERIOD) != userPreferences.getRefreshPeriod()) {
            setAlarm(this, userPreferences.getRefreshPeriod());
            editor.putInt(refreshPeriod, userPreferences.getRefreshPeriod());
        }
        editor.apply();
    }

    private void init() {
        listeners = new ArrayList<>();
        semesterList = new ArrayList<>();
        transcript = new ArrayList<>();
        internalData = null;
        preferences = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
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
    public void onInfoUpdated(UpdateMessage updateMessage) {

    }

    public void notifyRefresh(StudentData studentData) {
        semesterList = studentData.getSemesterList();
        student = studentData.getStudentInfo();
        transcript = studentData.getTranscript();
        notifyStudentInfoUpdated(student);
        notifySemesterListUpdated(semesterList);
        notifyTranscriptUpdated(transcript);
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

    public UserPreferences getUserPreferences() {
        return userPreferences;
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
