package ge.edu.freeuni.emis.emisapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import ge.edu.freeuni.emis.emisapp.R;

/**
 * Created by giorgi on 7/7/15.
 */
public class PlaceHolderFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_placeholderfrag, container, false);
    }
}
