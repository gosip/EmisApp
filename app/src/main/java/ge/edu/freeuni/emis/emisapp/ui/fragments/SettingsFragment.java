package ge.edu.freeuni.emis.emisapp.ui.fragments;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import ge.edu.freeuni.emis.emisapp.App;
import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.model.UserPreferences;

/**
 * Created by giorgi on 7/15/15.
 */
public class SettingsFragment extends Fragment {
    private ViewHolder viewHolder;
    private UserPreferences userPreferences;
    private App app;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.app = (App) getActivity().getApplication();
        this.userPreferences = app.getUserPreferences();
    }

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

        viewHolder.refreshSwitch.setChecked(userPreferences.isRefreshOn());
        if (userPreferences.isRefreshOn()) {
            viewHolder.refreshDuration.setVisibility(View.VISIBLE);
            viewHolder.notificationSwitch.setChecked(userPreferences.isNotificationsOn());
        } else {
            viewHolder.refreshDuration.setVisibility(View.GONE);
            viewHolder.notificationSwitch.setChecked(userPreferences.isNotificationsOn());
        }

        viewHolder.refreshSwitch.setChecked(userPreferences.isRefreshOn());

        viewHolder.refreshSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userPreferences.setRefreshOn(isChecked);
                if (isChecked) {
                    ObjectAnimator.ofInt(
                            viewHolder.refreshDuration,
                            "visibility",
                            View.VISIBLE
                    ).setDuration(300).start();
                    viewHolder.notificationSwitch.setChecked(userPreferences.isNotificationsOn());
                } else {
                    ObjectAnimator.ofInt(
                            viewHolder.refreshDuration,
                            "visibility",
                            View.GONE
                    ).setDuration(300).start();
                    userPreferences.setNotificationsOn(false);
                    viewHolder.notificationSwitch.setChecked(userPreferences.isNotificationsOn());
                }
                app.storePreferences();
            }
        });

        viewHolder.notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!userPreferences.isRefreshOn() && isChecked) {
                    Toast.makeText(getActivity(),
                            "refresh must be on in order to receive notifications",
                            Toast.LENGTH_SHORT).show();
                    viewHolder.notificationSwitch.setChecked(false);
                } else {
                    userPreferences.setNotificationsOn(isChecked);
                    app.storePreferences();
                }
            }
        });


        viewHolder.refreshDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int refreshPeriod = 0;
                if (position == 0) {
                    refreshPeriod = 5;
                } else if (position == 1) {
                    refreshPeriod = 15;
                } else if (position == 2) {
                    refreshPeriod = 30;
                } else if (position == 3) {
                    refreshPeriod = 60;
                } else if (position == 4) {
                    refreshPeriod = 12*60;
                }

                userPreferences.setRefreshPeriod(refreshPeriod);
                app.storePreferences();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setCorrectPosition();

        return rootView;
    }

    private void setCorrectPosition() {
        int refreshPeriod = userPreferences.getRefreshPeriod();
        int position = 0;
        if (refreshPeriod == 5) {
            position = 0;
        } else if (refreshPeriod == 15) {
            position = 1;
        } else if (refreshPeriod == 30) {
            position = 2;
        } else if (refreshPeriod == 60) {
            position = 3;
        } else if (refreshPeriod == 12*60) {
            position = 4;
        }
        viewHolder.refreshDuration.setSelection(position);
    }


    private static class ViewHolder{
        Switch refreshSwitch;
        Spinner refreshDuration;
        Switch notificationSwitch;
    }
}
