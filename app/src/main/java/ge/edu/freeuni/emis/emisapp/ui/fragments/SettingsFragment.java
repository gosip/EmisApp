package ge.edu.freeuni.emis.emisapp.ui.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import ge.edu.freeuni.emis.emisapp.R;

/**
 * Created by giorgi on 7/15/15.
 */
public class SettingsFragment extends Fragment {
    private ViewHolder viewHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        viewHolder = new ViewHolder();
        viewHolder.refreshSwitch = (Switch) rootView.findViewById(R.id.switch_refres);
        viewHolder.refreshDuration = (Spinner) rootView.findViewById(R.id.refresh_duration);
        viewHolder.notificationSwitch = (Switch) rootView.findViewById(R.id.switch_notifications);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.duration_indicators, android.R.layout.simple_spinner_item);
        viewHolder.refreshDuration.setAdapter(adapter);

        viewHolder.refreshSwitch.setChecked(true);

        viewHolder.refreshSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ObjectAnimator.ofInt(
                            viewHolder.refreshDuration,
                            "visibility",
                            View.VISIBLE
                    ).setDuration(300).start();
                } else {
                    ObjectAnimator.ofInt(
                            viewHolder.refreshDuration,
                            "visibility",
                            View.GONE
                    ).setDuration(300).start();
                }
            }
        });
        return rootView;
    }

    private static class ViewHolder{
        Switch refreshSwitch;
        Spinner refreshDuration;
        Switch notificationSwitch;
    }
}
