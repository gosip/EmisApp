package ge.edu.freeuni.emis.emisapp.loaders;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.interfaces.TranscriptLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.TranscriptLoadingSubject;
import ge.edu.freeuni.emis.emisapp.loaders.util.JsoupUtils;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;

public class TranscriptLoader extends AsyncTask implements TranscriptLoadingSubject {

    private TranscriptLoadingListener listener = null;
    private Context context;

    @Override
    protected Object doInBackground(Object[] params) {
        List<TranscriptRow> transcriptRows = new ArrayList<>();
        Document document = JsoupUtils.getDocumentFromFile("grades_form.html", context);
        Elements tableRows = document.getElementsByClass("fr_table").first()
                .getElementsByTag("tbody").first().children();
        for (int i = 0; i < tableRows.size() - 2; ++i) {
            TranscriptRow transcriptRow = new TranscriptRow();
            Elements currRowFields = tableRows.get(i).children();
            transcriptRow.setClassCode(currRowFields.get(0).text());
            transcriptRow.setClassName(currRowFields.get(1).text());
            transcriptRow.setSemesterName(currRowFields.get(2).text());
            double score = Double.parseDouble(currRowFields.get(3).text());
            String indicator = currRowFields.get(4).child(0).text();
            transcriptRow.setStudentsGrade(new Grade(indicator,score));
            transcriptRow.setNumCredits(Integer.parseInt(currRowFields.get(5).text()));
            transcriptRow.setCreditsEarned(Integer.parseInt(currRowFields.get(6).text()));
            transcriptRow.setScoreEarned(Double.parseDouble(currRowFields.get(7).text()));
            transcriptRows.add(transcriptRow);
        }
        return transcriptRows;
    }

    @Override
    protected void onPostExecute(Object o) {
        notifyTranscriptDownloaded((List<TranscriptRow>) o);
    }

    @Override
    public void registerListener(TranscriptLoadingListener listener) {
        this.listener = listener;
    }

    @Override
    public void unRegisterListener(TranscriptLoadingListener listener) {
        this.listener = null;
    }

    @Override
    public void notifyTranscriptDownloaded(List<TranscriptRow> transcript) {
        if (this.listener != null)
            this.listener.onTranscriptDownloaded(transcript);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
