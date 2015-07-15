package ge.edu.freeuni.emis.emisapp.loaders;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonWriter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ge.edu.freeuni.emis.emisapp.interfaces.InfoUpdatingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.InfoUpdatingSubject;
import ge.edu.freeuni.emis.emisapp.interfaces.UpdateMessage;
import ge.edu.freeuni.emis.emisapp.loaders.enums.GradeJSONType;
import ge.edu.freeuni.emis.emisapp.loaders.enums.StudentInfoJSONType;
import ge.edu.freeuni.emis.emisapp.loaders.enums.TranscriptJSONType;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.model.grading.Category;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;
import ge.edu.freeuni.emis.emisapp.model.grading.SingleDetailedGrade;

public class InfoUpdater extends AsyncTask implements InfoUpdatingSubject {

    private InfoUpdatingListener listener = null;
    private Context context;

    private StudentData retrievedData;
    private StudentData webData;

    public InfoUpdater(Context context, StudentData retrievedData, StudentData webData) {
        this.retrievedData = retrievedData;
        this.webData = webData;
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        UpdateMessage message = UpdateMessage.NO;

        boolean infoChanged = !retrievedData.getStudentInfo().equals(webData.getStudentInfo());
        boolean gradesChanged = !retrievedData.getSemesterList().equals(webData.getSemesterList());
        boolean transcriptChanged = !retrievedData.getTranscript().equals(webData.getTranscript());

        if (infoChanged) {
            savePersonalInfo(webData.getStudentInfo());
            message =  UpdateMessage.PERSONAL_INFO;
        }
        if (transcriptChanged) {
            saveTranscript(webData.getTranscript());
            message = UpdateMessage.TRANSCRIPT;
        }
        if (gradesChanged) {
            saveGrades(webData.getSemesterList());
            message = UpdateMessage.GRADE;
        }
        return message;
    }

    private void savePersonalInfo(Student studentInfo) {
        try {
            FileOutputStream out =
                    context.openFileOutput(Filename.PERSONAL_INFO_FILE, Context.MODE_PRIVATE);
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(out));
            writer.beginObject();
            writer.name(StudentInfoJSONType.NAME.name()).value(studentInfo.getStudentName());
            writer.name(StudentInfoJSONType.MAJOR.name()).value(studentInfo.getMajor());
            writer.name(StudentInfoJSONType.DEGREE.name()).value(studentInfo.getExpectedDegree());
            writer.name(StudentInfoJSONType.SEMESTER.name()).value(studentInfo.getCurrSemester());
            writer.name(StudentInfoJSONType.GENDER.name()).value(studentInfo.getGender());
            writer.name(StudentInfoJSONType.NATION.name()).value(studentInfo.getNationality());
            writer.name(StudentInfoJSONType.BIRTHDAY.name()).value(studentInfo.getBirthDate());
            writer.name(StudentInfoJSONType.ADDRESS.name()).value(studentInfo.getAddress());
            writer.name(StudentInfoJSONType.STATUS.name()).value(studentInfo.getStatus());
            writer.name(StudentInfoJSONType.SCHOOL.name()).value(studentInfo.getSchoolName());
            writer.name(StudentInfoJSONType.CREDITS.name()).value(studentInfo.getNumCredits());
            writer.name(StudentInfoJSONType.GPA.name()).value(studentInfo.getGPA());
            writer.endObject();
            writer.flush();
            writer.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTranscript(List<TranscriptRow> transcript) {
        try {
            FileOutputStream out =
                    context.openFileOutput(Filename.TRANSCRIPT_FILE, Context.MODE_PRIVATE);
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(out));
            writer.beginArray();
            for (int i = 0; i < transcript.size(); ++i) {
                saveTranscriptRow(writer, transcript.get(i));
            }
            writer.endArray();
            writer.flush();
            writer.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTranscriptRow(JsonWriter writer, TranscriptRow row) throws IOException {
        writer.beginObject();
        writer.name(TranscriptJSONType.CODE.name()).value(row.getClassCode());
        writer.name(TranscriptJSONType.NAME.name()).value(row.getClassName());
        writer.name(TranscriptJSONType.SEMESTER.name()).value(row.getSemesterName());
        writer.name(TranscriptJSONType.PERCENTAGE.name()).value(row.getStudentsGrade().getScore());
        writer.name(TranscriptJSONType.GRADE.name()).value(row.getStudentsGrade().getGradeIndicator());
        writer.name(TranscriptJSONType.CRED.name()).value(row.getNumCredits());
        writer.name(TranscriptJSONType.CRED_EARNED.name()).value(row.getCreditsEarned());
        writer.name(TranscriptJSONType.SCORE.name()).value(row.getScoreEarned());
        writer.endObject();
    }

    private void saveGrades(List<Semester> semesterList) {
        try {
            FileOutputStream out =
                    context.openFileOutput(Filename.GRADES_FILE, Context.MODE_PRIVATE);
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(out));
            writer.beginArray();
            for (int i = 0; i < semesterList.size(); ++i) {
                saveSemester(writer, semesterList.get(i));
            }
            writer.endArray();
            writer.flush();
            writer.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSemester(JsonWriter writer, Semester semester) throws IOException {
        writer.beginObject();
        writer.name(GradeJSONType.SEMESTER.name()).value(semester.getNumSemester());
        writer.name(GradeJSONType.CLASS_LIST.name());
        saveClassList(writer, semester.getClasses());
        writer.endObject();
    }

    private void saveClassList(JsonWriter writer, List<Class> classList) throws IOException {
        writer.beginArray();
        for (int i = 0; i < classList.size(); ++i) {
            Class cl = classList.get(i);
            writer.beginObject();
            writer.name(GradeJSONType.NAME.name()).value(cl.getClassName());
            writer.name(GradeJSONType.CRED.name()).value(cl.getNumCredits());
            writer.name(GradeJSONType.GRADE.name());
            saveGrade(writer, cl.getStudentsGrade());
            writer.name(GradeJSONType.LECTURERS.name());
            saveLecturers(writer, cl.getLecturers());
            writer.name(GradeJSONType.DETAILED.name());
            saveDetailedGrade(writer, cl.getDetailedGradeCategories(), cl.getDetailedGrades());
            writer.endObject();
        }
        writer.endArray();
    }

    private void saveGrade(JsonWriter writer, Grade grade) throws IOException {
        writer.beginObject();
        writer.name(GradeJSONType.INDICATOR.name()).value(grade.getGradeIndicator());
        writer.name(GradeJSONType.TOTAL_SCORE.name()).value(grade.getScore());
        writer.endObject();
    }

    private void saveLecturers(JsonWriter writer, List<String> lecturers) throws IOException {
        writer.beginArray();
        for (int i = 0; i < lecturers.size(); ++i) {
            writer.beginObject();
            writer.name(GradeJSONType.LECTURER_NAME.name()).value(lecturers.get(i));
            writer.endObject();
        }
        writer.endArray();
    }

    private void saveDetailedGrade(JsonWriter writer, List<Category> categories,
                                   Map<String, List<SingleDetailedGrade>> map) throws IOException {
        Map<String, Category> categoryMap = categoryMap(categories);
        writer.beginArray();
        for (Map.Entry<String, List<SingleDetailedGrade>> pair:  map.entrySet()) {
            Category category = categoryMap.get(pair.getKey());
            List<SingleDetailedGrade> gradeList = pair.getValue();
            for (int i = 0; i < gradeList.size(); ++i) {
                writer.beginObject();
                writer.name(GradeJSONType.CATEGORY.name()).value(category.getCategoryIndicator());
                writer.name(GradeJSONType.CATEGORY_SCORE.name()).value(category.getCategoryScore());
                writer.name(GradeJSONType.DETAILED_NUMBER.name()).value(gradeList.get(i).getGradeNumber());
                writer.name(GradeJSONType.DETAILED_SCORE.name()).value(gradeList.get(i).getScore());
                writer.name(GradeJSONType.DETAILED_MAXSCORE.name()).value(gradeList.get(i).getMaxScore());
                writer.name(GradeJSONType.DETAILED_WEIGHT.name()).value(gradeList.get(i).getWeight());
                writer.name(GradeJSONType.DETAILED_RESULT.name()).value(gradeList.get(i).getResult());
                writer.endObject();
            }
        }
        writer.endArray();
    }

    private Map<String, Category> categoryMap(List<Category> categories) {
        Map<String, Category> map = new HashMap<>();
        for (int i = 0; i < categories.size(); ++i) {
            Category category = categories.get(i);
            map.put(category.getCategoryIndicator(), category);
        }
        return map;
    }

    @Override
    protected void onPostExecute(Object o) {
        notifyInfoUpdated((UpdateMessage)o);
    }

    @Override
    public void registerListener(InfoUpdatingListener listener) {
        this.listener = listener;
    }

    @Override
    public void unRegisterListener(InfoUpdatingListener listener) {
        this.listener = null;
    }

    @Override
    public void notifyInfoUpdated(UpdateMessage updateMessage) {
        if (listener != null)
            listener.notifyInfoUpdated(updateMessage);
    }
}
