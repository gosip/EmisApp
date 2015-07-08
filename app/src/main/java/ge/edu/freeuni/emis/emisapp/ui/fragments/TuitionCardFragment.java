package ge.edu.freeuni.emis.emisapp.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.App;
import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.adapters.SemesterListAdapter;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateListener;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateSubject;
import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.ui.activities.ClassesActivity;

/**
 * Created by giorgi on 7/7/15.
 */
public class TuitionCardFragment extends Fragment implements AppStateListener {
    private List<Semester> semesterList;
    private AppStateSubject subject;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        semesterList = ((App)activity.getApplication()).getSemesterList();
        subject = (AppStateSubject) activity.getApplication();
        subject.registerListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        subject.unRegisterListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ExpandableListView listView = (ExpandableListView) inflater
                .inflate(R.layout.fragment_semester_list, container, false);
        SemesterListAdapter adapter = new SemesterListAdapter(getActivity(), semesterList);

        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Intent intent = new Intent(getActivity(), ClassesActivity.class);
                intent.putExtra(getResources().getString(R.string.semester_key), groupPosition);
                intent.putExtra(getResources().getString(R.string.class_key), childPosition);

                startActivity(intent);
                return true;
            }
        });
        return listView;
    }

    @Override
    public void onSemestersListDownloaded(List<Semester> semesterList) {

    }
}
