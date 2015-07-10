package ge.edu.freeuni.emis.emisapp.interfaces;

/**
 * Created by User on 7/10/2015.
 */
public interface WebLoaderSubject {
    public void registerListener(WebLoaderListener listener);
    public void unRegisterListener(WebLoaderListener listener);
    public void notifyWebDataLoaded(WebLoaderListener listener);
}
