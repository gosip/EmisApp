package ge.edu.freeuni.emis.emisapp.loaders;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.interfaces.InfoLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.InfoLoadingSubject;
import ge.edu.freeuni.emis.emisapp.loaders.enums.GradeJSONType;
import ge.edu.freeuni.emis.emisapp.loaders.enums.StudentInfoJSONType;
import ge.edu.freeuni.emis.emisapp.loaders.enums.TranscriptJSONType;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;
import ge.edu.freeuni.emis.emisapp.model.grading.SingleDetailedGrade;

public class InfoLoader extends AsyncTask implements InfoLoadingSubject {

    private InfoLoadingListener listener;

    private Context context;

    public InfoLoader(Context context) {
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Student studentInfo = new Student();
        List<Semester> semesterList = new ArrayList<>();
        List<TranscriptRow> transcript = new ArrayList<>();

        String[] fileList = context.fileList();
        if (fileList.length > 0) { // there is data to retrieve
            try {
                retrieveStudentInfo(studentInfo);
                retrieveSemesterList(semesterList);
                retrieveTranscript(transcript);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new StudentData(studentInfo, semesterList, transcript);
    }

    private void retrieveStudentInfo(Student studentInfo) throws IOException {
        FileInputStream in = context.openFileInput(Filename.PERSONAL_INFO_FILE);
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        reader.beginObject();
        while (reader.hasNext()) {
            String st = reader.nextName();
            StudentInfoJSONType type = StudentInfoJSONType.valueOf(st);
            switch (type) {
                case NAME: studentInfo.setStudentName(reader.nextString()); break;
                case MAJOR: studentInfo.setMajor(reader.nextString()); break;
                case DEGREE: studentInfo.setExpectedDegree(reader.nextString()); break;
                case SEMESTER: studentInfo.setCurrSemester(reader.nextInt()); break;
                case GENDER: studentInfo.setGender(reader.nextString()); break;
                case NATION: studentInfo.setNationality(reader.nextString()); break;
                case BIRTHDAY: studentInfo.setBirthDate(reader.nextString()); break;
                case ADDRESS: studentInfo.setAddress(reader.nextString()); break;
                case STATUS: studentInfo.setStatus(reader.nextString()); break;
                case SCHOOL: studentInfo.setSchoolName(reader.nextString()); break;
                case CREDITS: studentInfo.setNumCredits(reader.nextInt()); break;
                case GPA: studentInfo.setGPA(reader.nextDouble()); break;
            }
        }
        reader.endObject();
        reader.close();
        in.close();
    }

    private void retrieveSemesterList(List<Semester> semesterList) throws IOException {
        FileInputStream in = context.openFileInput(Filename.GRADES_FILE);
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        reader.beginArray();
        while (reader.hasNext()) {
            Semester semester = new Semester();
            reader.beginObject();
            while (reader.hasNext()) {
                GradeJSONType type = GradeJSONType.valueOf(reader.nextName());
                switch (type) {
                    case SEMESTER:
                        semester.setNumSemester(reader.nextInt());
                        break;
                    case CLASS_LIST:
                        semester.setClasses(readClasses(reader));
                        break;
                }
            }
            reader.endObject();
            semesterList.add(semester);
        }
        reader.endArray();
        reader.close();
        in.close();
    }

    private List<Class> readClasses(JsonReader reader) throws IOException {
        List<Class> classList = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            Class cl = new Class();
            while (reader.hasNext()) {
                GradeJSONType type = GradeJSONType.valueOf(reader.nextName());
                switch (type) {
                    case NAME:
                        cl.setClassName(reader.nextString());
                        break;
                    case CRED:
                        cl.setNumCredits(reader.nextInt());
                        break;
                    case GRADE:
                        cl.setStudentsGrade(readGrade(reader));
                        break;
                    case LECTURERS:
                        cl.setLecturers(readLecturers(reader));
                        break;
                    case DETAILED:
                        getDetailedGrade(reader, cl);
                        break;
                }
            }
            classList.add(cl);
            reader.endObject();
        }
        reader.endArray();
        return classList;
    }

    private Grade readGrade(JsonReader reader) throws IOException {
        String indicator = "";
        double score = 0;
        reader.beginObject();
        while (reader.hasNext()) {
            GradeJSONType type = GradeJSONType.valueOf(reader.nextName());
            switch (type) {
                case INDICATOR: indicator = reader.nextString(); break;
                case TOTAL_SCORE: score = reader.nextDouble(); break;
            }
        }
        reader.endObject();
        return new Grade(indicator, score);
    }

    private List<String> readLecturers(JsonReader reader) throws IOException {
        List<String> lecturers = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            if (GradeJSONType.valueOf(reader.nextName()) == GradeJSONType.LECTURER_NAME)
                lecturers.add(reader.nextString());
            reader.endObject();
        }
        reader.endArray();
        return lecturers;
    }

    private void getDetailedGrade(JsonReader reader, Class cl) throws IOException {
        reader.beginArray();
        String category = "";
        double catScore = 0;
        int gradeNumber = 0;
        double score = 0;
        double maxScore = 0;
        double weight = 0;
        double result = 0;
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                GradeJSONType type = GradeJSONType.valueOf(reader.nextName());
                switch (type) {
                    case CATEGORY: category = reader.nextString(); break;
                    case CATEGORY_SCORE: catScore = reader.nextDouble(); break;
                    case DETAILED_NUMBER: gradeNumber = reader.nextInt(); break;
                    case DETAILED_SCORE: score = reader.nextDouble(); break;
                    case DETAILED_MAXSCORE: maxScore = reader.nextDouble(); break;
                    case DETAILED_WEIGHT: weight = reader.nextDouble(); break;
                    case DETAILED_RESULT: result = reader.nextDouble(); break;
                }
            }
            cl.addDetailedGrade(category, new SingleDetailedGrade(
                            gradeNumber, score, maxScore, weight, result),
                    catScore);
            reader.endObject();
        }
        reader.endArray();
    }

    private void retrieveTranscript(List<TranscriptRow> transcript) throws IOException {
        FileInputStream in = context.openFileInput(Filename.TRANSCRIPT_FILE);
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        reader.beginArray();
        while (reader.hasNext()) {
            transcript.add(readTranscriptRow(reader));
        }
        reader.endArray();
        reader.close();
        in.close();
    }

    private TranscriptRow readTranscriptRow(JsonReader reader) throws IOException {
        TranscriptRow row = new TranscriptRow();
        String indicator = "";
        double score = 0;
        reader.beginObject();
        while (reader.hasNext()) {
            TranscriptJSONType type = TranscriptJSONType.valueOf(reader.nextName());
            switch (type) {
                case CODE: row.setClassCode(reader.nextString()); break;
                case NAME: row.setClassName(reader.nextString()); break;
                case SEMESTER: row.setSemesterName(reader.nextString()); break;
                case PERCENTAGE: score = reader.nextDouble(); break;
                case GRADE: indicator = reader.nextString(); break;
                case CRED: row.setNumCredits(reader.nextInt()); break;
                case CRED_EARNED: row.setCreditsEarned(reader.nextInt()); break;
                case SCORE: row.setScoreEarned(reader.nextDouble()); break;
            }
        }
        row.setStudentsGrade(new Grade(indicator, score));
        reader.endObject();
        return row;
    }

    @Override
    protected void onPostExecute(Object o) {
        notifyInfoLoaded((StudentData)o);
    }

    @Override
    public void registerListener(InfoLoadingListener listener) {
        this.listener = listener;
    }

    @Override
    public void unRegisterListener(InfoLoadingListener listener) {
        this.listener = null;
    }

    @Override
    public void notifyInfoLoaded(StudentData studentData) {
        if (listener != null) {
            listener.onInfoLoaded(studentData);
        }
    }
}
