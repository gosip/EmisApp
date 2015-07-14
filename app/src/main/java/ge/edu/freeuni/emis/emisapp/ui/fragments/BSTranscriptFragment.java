package ge.edu.freeuni.emis.emisapp.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.App;
import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.adapters.BSTranscriptListAdapter;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateListener;
import ge.edu.freeuni.emis.emisapp.interfaces.AppStateSubject;
import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.model.Student;
import ge.edu.freeuni.emis.emisapp.model.TranscriptRow;

/**
 * Created by giorgi on 7/14/15.
 */
public class BSTranscriptFragment extends Fragment implements AppStateListener {
    private List<TranscriptRow> transcript;
    private BSTranscriptListAdapter adapter;
    private AppStateSubject subject;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        transcript = ((App) activity.getApplication()).getTranscript();
        subject = (AppStateSubject) activity.getApplication();
        adapter = new BSTranscriptListAdapter(getActivity(), transcript);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView view = (ListView) inflater.inflate(R.layout.fragment_bs_transcript, container, false);
        view.setAdapter(adapter);
        return view;
    }

    @Override
    public void onSemestersListUpdated(List<Semester> semesterList) {

    }

    @Override
    public void onStudentInfoUpdated(Student student) {

    }

    @Override
    public void onTranscriptUpdated(List<TranscriptRow> transcript) {
        this.transcript = transcript;
        adapter.setList(transcript);
    }
}
