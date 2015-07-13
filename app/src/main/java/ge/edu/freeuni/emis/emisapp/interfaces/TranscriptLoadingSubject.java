package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.TranscriptRow;

public interface TranscriptLoadingSubject {
    public void registerListener(TranscriptLoadingListener listener);
    public void unRegisterListener(TranscriptLoadingListener listener);
    public void notifyTranscriptDownloaded(List<TranscriptRow> transcript);
}
