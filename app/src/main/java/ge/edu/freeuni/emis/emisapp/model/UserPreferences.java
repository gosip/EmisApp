package ge.edu.freeuni.emis.emisapp.model;

/**
 * Created by giorgi on 7/15/15.
 */
public class UserPreferences {
    private boolean refreshOn;
    private boolean notificationsOn;
    private int refreshPeriod;

    // setters
    public void setRefreshOn(boolean refreshOn) {
        this.refreshOn = refreshOn;
    }

    public void setNotificationsOn(boolean notificationsOn) {
        this.notificationsOn = notificationsOn;
    }

    public void setRefreshPeriod(int refreshPeriod) {
        this.refreshPeriod = refreshPeriod;
    }

    // getters
    public boolean isRefreshOn() {
        return refreshOn;
    }

    public boolean isNotificationsOn() {
        return notificationsOn;
    }

    public int getRefreshPeriod() {
        return refreshPeriod;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserPreferences{");
        sb.append("refreshOn=").append(refreshOn);
        sb.append(", notificationsOn=").append(notificationsOn);
        sb.append(", refreshPeriod=").append(refreshPeriod);
        sb.append('}');
        return sb.toString();
    }
}
