package ge.edu.freeuni.emis.emisapp.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.App;
import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateListener;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateSubject;
import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.model.Student;
import ge.edu.freeuni.emis.emisapp.model.TranscriptRow;

/**
 * Created by giorgi on 7/10/15.
 */
public class StudentInfoFragment extends Fragment implements AppStateListener {
    private App app;
    private ViewHolder viewHolder = null;
    private View rootView = null;
    private Student studentInfo;
    private AppStateSubject subject;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        app = (App) activity.getApplication();
        subject = app;
        studentInfo = app.getStudent();

        subject.registerListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        subject.unRegisterListener(this);
        rootView = null;
        viewHolder = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_student_info, container, false);
        viewHolder = new ViewHolder();
//        viewHolder.studentImg = (ImageView) rootView.findViewById(R.id.student_avatar);
        viewHolder.studentName = (TextView) rootView.findViewById(R.id.student_name);
        viewHolder.studentMajor = (TextView) rootView.findViewById(R.id.student_major);
        viewHolder.studentDegree = (TextView) rootView.findViewById(R.id.student_degree);
        viewHolder.studentSemester = (TextView) rootView.findViewById(R.id.student_semester);
        viewHolder.studentGender = (TextView) rootView.findViewById(R.id.student_gender);
        viewHolder.studentNationality = (TextView) rootView.findViewById(R.id.student_nationality);
        viewHolder.studentBirthDate = (TextView) rootView.findViewById(R.id.student_birth_date);
        viewHolder.studentAddress = (TextView) rootView.findViewById(R.id.student_address);
        viewHolder.studentStatus = (TextView) rootView.findViewById(R.id.student_status);
        viewHolder.studentSchool = (TextView) rootView.findViewById(R.id.student_school);
        viewHolder.studentCredits = (TextView) rootView.findViewById(R.id.student_credits);
        viewHolder.studentGpa = (TextView) rootView.findViewById(R.id.student_gpa);

        if (studentInfo != null) {
            updateStudentInfo();
        }


        return rootView;
    }

    private void updateStudentInfo() {
//        viewHolder.studentImg.setImageBitmap(studentInfo.getProfileImg());
        viewHolder.studentName.setText(studentInfo.getStudentName());
        viewHolder.studentMajor.setText(studentInfo.getMajor());
        viewHolder.studentDegree.setText(studentInfo.getExpectedDegree());
        viewHolder.studentSemester.setText("" + studentInfo.getCurrSemester());
        viewHolder.studentGender.setText(studentInfo.getGender());
        viewHolder.studentNationality.setText(studentInfo.getNationality());
        viewHolder.studentBirthDate.setText(studentInfo.getBirthDate());
        viewHolder.studentAddress.setText(studentInfo.getAddress());
        viewHolder.studentStatus.setText(studentInfo.getStatus());
        viewHolder.studentSchool.setText(studentInfo.getSchoolName());
        viewHolder.studentCredits.setText("" + studentInfo.getNumCredits());
        viewHolder.studentGpa.setText("" + studentInfo.getGPA());
    }

    private static class ViewHolder {
//        ImageView studentImg;
        TextView studentName;
        TextView studentMajor;
        TextView studentDegree;
        TextView studentSemester;
        TextView studentGender;
        TextView studentNationality;
        TextView studentBirthDate;
        TextView studentAddress;
        TextView studentStatus;
        TextView studentSchool;
        TextView studentCredits;
        TextView studentGpa;
    }

    @Override
    public void onSemestersListUpdated(List<Semester> semesterList) {

    }

    @Override
    public void onStudentInfoUpdated(Student student) {
        this.studentInfo = student;
        if (viewHolder != null) {
            updateStudentInfo();
        }
    }

    @Override
    public void onTranscriptUpdated(List<TranscriptRow> transcript) {

    }
}
