package ge.edu.freeuni.emis.emisapp.interfaces;

import ge.edu.freeuni.emis.emisapp.model.Student;

public interface PersonalInfoLoadingListener {
    public void onPersonalInfoDownloaded(Student studentInfo);
}
