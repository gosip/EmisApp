package ge.edu.freeuni.emis.emisapp.interfaces;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.model.Student;
import ge.edu.freeuni.emis.emisapp.model.StudentData;
import ge.edu.freeuni.emis.emisapp.model.TranscriptRow;

public interface InfoUpdatingListener {
    public void notifyInfoUpdated(UpdateMessage updateMessage);
}
