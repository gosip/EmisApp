package ge.edu.freeuni.emis.emisapp.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import ge.edu.freeuni.emis.emisapp.App;
import ge.edu.freeuni.emis.emisapp.R;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null && intent.getAction().
                equals("android.intent.action.BOOT_COMPLETED")) {
            SharedPreferences preferences = context.getSharedPreferences(
                    context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            if (preferences.getBoolean(context.getString(R.string.refresh_on), false)) {
                App.setAlarm(context, preferences.getInt(context.getString(R.string.refresh_period),
                        App.DEFAULT_REFRESH_PERIOD));
            }
        }
    }
}
