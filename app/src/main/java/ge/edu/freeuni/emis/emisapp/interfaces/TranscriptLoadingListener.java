package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.TranscriptRow;

public interface TranscriptLoadingListener {
    public void onTranscriptDownloaded(List<TranscriptRow> transcript);
}
