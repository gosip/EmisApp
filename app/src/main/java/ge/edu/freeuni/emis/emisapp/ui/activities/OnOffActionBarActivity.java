package ge.edu.freeuni.emis.emisapp.ui.activities;

import android.support.v7.app.ActionBarActivity;

import ge.edu.freeuni.emis.emisapp.App;

public abstract class OnOffActionBarActivity extends ActionBarActivity {
    @Override
    protected void onResume() {
        super.onResume();
        App.activityOn();
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.activityOff();
    }
}
