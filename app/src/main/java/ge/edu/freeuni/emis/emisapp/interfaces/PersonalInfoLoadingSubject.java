package ge.edu.freeuni.emis.emisapp.interfaces;

import ge.edu.freeuni.emis.emisapp.model.Student;

public interface PersonalInfoLoadingSubject {
    public void registerListener(PersonalInfoLoadingListener listener);
    public void unRegisterListener(PersonalInfoLoadingListener listener);
    public void notifyPersonalInfoDownloaded(Student studentInfo);
}
