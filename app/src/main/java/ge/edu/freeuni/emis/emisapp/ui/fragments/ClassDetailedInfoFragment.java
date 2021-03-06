package ge.edu.freeuni.emis.emisapp.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.App;
import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.adapters.DetailedGradesListAdapter;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateListener;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;

/**
 * Created by giorgi on 7/9/15.
 */
public class ClassDetailedInfoFragment extends Fragment implements AppStateListener {
    private App app;
    private int semesterIdx;
    private int classIdx;
    public static ClassDetailedInfoFragment newInstance(int semesterIdx, int classIdx) {
        ClassDetailedInfoFragment ret = new ClassDetailedInfoFragment();
        ret.semesterIdx = semesterIdx;
        ret.classIdx = classIdx;
        return ret;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.semesterIdx = savedInstanceState.getInt("semester");
            this.classIdx = savedInstanceState.getInt("class");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("semester", semesterIdx);
        outState.putInt("class", classIdx);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        app = (App) activity.getApplication();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout rootView = (LinearLayout) inflater
                .inflate(R.layout.fragment_class_detailed_view, container, false);
        TextView className = (TextView) rootView.findViewById(R.id.class_name_text);
        TextView grade = (TextView) rootView.findViewById(R.id.grade);
        TextView credits = (TextView) rootView.findViewById(R.id.credits);
        ExpandableListView gradeList = (ExpandableListView) rootView
                .findViewById(R.id.detailed_grades_list);

        Class currClass = app.getSemesterList().get(semesterIdx).getClasses().get(classIdx);

        className.setText(currClass.getClassName());
        grade.setText(currClass.getStudentsGrade().getGradeIndicator()
                + " - " + currClass.getStudentsGrade().getScore());
        credits.setText("credits: " + currClass.getNumCredits());

        gradeList.setAdapter(new DetailedGradesListAdapter(
                getActivity(),
                currClass.getDetailedGradeCategories(),
                currClass.getDetailedGrades()));

        return rootView;
    }

    @Override
    public void onSemestersListUpdated(List<Semester> semesterList) {

    }

    @Override
    public void onStudentInfoUpdated(Student student) {

    }

    @Override
    public void onTranscriptUpdated(List<TranscriptRow> transcript) {

    }
}
