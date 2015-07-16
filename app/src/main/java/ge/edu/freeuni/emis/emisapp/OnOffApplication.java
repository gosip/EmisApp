package ge.edu.freeuni.emis.emisapp;

import android.app.Application;

public class OnOffApplication extends Application {

    private static boolean isAlive = false;

    public static void activityOn() {
        isAlive = true;
    }

    public static void activityOff() {
        isAlive = false;
    }

    public static boolean isOn() {
        return isAlive;
    }
}
