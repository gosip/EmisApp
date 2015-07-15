package ge.edu.freeuni.emis.emisapp.model;

import java.util.List;

public class StudentData {

    private Student studentInfo;
    private List<Semester> semesterList;
    private List<TranscriptRow> transcript;

    public StudentData(Student studentInfo, List<Semester> semesterList, List<TranscriptRow> transcript) {
        this.studentInfo = studentInfo;
        this.semesterList = semesterList;
        this.transcript = transcript;
    }

    public Student getStudentInfo() {
        return studentInfo;
    }

    public List<Semester> getSemesterList() {
        return semesterList;
    }

    public List<TranscriptRow> getTranscript() {
        return transcript;
    }
}
