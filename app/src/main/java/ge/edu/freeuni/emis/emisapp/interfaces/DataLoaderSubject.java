package ge.edu.freeuni.emis.emisapp.interfaces;

/**
 * Created by User on 7/10/2015.
 */
public interface DataLoaderSubject {
    public void registerListener(DataLoaderSubject listener);
    public void unRegisterListener(DataLoaderSubject listener);
    public void notifyDataLoaded(DataLoaderSubject listener);
}
