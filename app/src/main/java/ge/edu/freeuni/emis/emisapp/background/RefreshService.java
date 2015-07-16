package ge.edu.freeuni.emis.emisapp.background;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.App;
import ge.edu.freeuni.emis.emisapp.R;
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
import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.model.Student;
import ge.edu.freeuni.emis.emisapp.model.StudentData;
import ge.edu.freeuni.emis.emisapp.model.TranscriptRow;
import ge.edu.freeuni.emis.emisapp.ui.activities.MainActivity;

public class RefreshService extends IntentService implements PersonalInfoLoadingListener,
        GradesLoadingListener, TranscriptLoadingListener, InfoLoadingListener, InfoUpdatingListener {

    private static final String WORKER_THREAD = "ge.edu.freeuni.emis.refresh";

    private List<Semester> semesterList;
    private Student studentInfo;
    private List<TranscriptRow> transcript;
    private StudentData internalData;

    public RefreshService() {
        super(WORKER_THREAD);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (App.isOn()) return;

        InfoLoader infoLoader = new InfoLoader(this);
        infoLoader.registerListener(this);
        infoLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

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

    @Override
    public void onInfoLoaded(StudentData studentData) {
        this.internalData = studentData;
    }

    @Override
    public void onGradesDownloaded(List<Semester> semesterList) {
        this.semesterList = semesterList;
    }

    @Override
    public void onPersonalInfoDownloaded(Student studentInfo) {
        this.studentInfo = studentInfo;
    }

    @Override
    public void onTranscriptDownloaded(List<TranscriptRow> transcript) {
        this.transcript = transcript;
        initiateUpdate();
    }

    private void initiateUpdate() {
        InfoUpdater updater = new InfoUpdater(this, internalData,
                new StudentData(this.studentInfo, this.semesterList, this.transcript));
        updater.registerListener(this);
        updater.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Override
    public void onInfoUpdated(UpdateMessage updateMessage) {
        if (updateMessage != UpdateMessage.NO) notifyUser(updateMessage);
    }

    private void notifyUser(UpdateMessage updateMessage) {
        SharedPreferences preferences =
                getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        if (!preferences.getBoolean(getString(R.string.notification_pref), false)) return;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("FreeUni Emis").setAutoCancel(true);
        Intent intent = new Intent(this, MainActivity.class);
        switch (updateMessage) {
            case GRADE:
                builder.setSmallIcon(R.drawable.semesters);
                builder.setContentText("You've got a new grade");
                intent.putExtra(MainActivity.FRAGMENT_POSITION_EXTRA, MainActivity.TUITION_CARD_FRAGMENT);
                break;
            case TRANSCRIPT:
                builder.setSmallIcon(R.drawable.bs_transcript);
                builder.setContentText("Your transcript has changed");
                intent.putExtra(MainActivity.FRAGMENT_POSITION_EXTRA, MainActivity.TRANSCRIPT_FRAGMENT);
                break;
            case PERSONAL_INFO:
                builder.setSmallIcon(R.drawable.user);
                builder.setContentText("You pass another course\n+" +
                        "or changed sex");
                intent.putExtra(MainActivity.FRAGMENT_POSITION_EXTRA, MainActivity.STUDENT_INFO_FRAGMENT);
                break;
        }
        builder.setContentIntent(PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT));
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

    }

}
