package ge.edu.freeuni.emis.emisapp.loaders;

import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.interfaces.GradesLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.GradesLoadingSubject;
import ge.edu.freeuni.emis.emisapp.loaders.util.JsoupUtils;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;

public class GradesLoader extends AsyncTask implements GradesLoadingSubject {

    private GradesLoadingListener listener = null;
    // for mocking purposes
    private Context context;

    @Override
    protected Object doInBackground(Object[] params) {
        List<Semester> semesterList = new ArrayList<Semester>();
        Document document = JsoupUtils.getDocumentFromFile("grades.htm", context);
        Elements semesterBlocks = document.getElementsByClass("sem_block");
        for (int i = 0; i < semesterBlocks.size(); ++i) {
            Element currBlock = semesterBlocks.get(i);
            Semester currSemester = new Semester();
            List<Class> classList = new ArrayList<Class>();
            String semesterTitle = currBlock.getElementsByClass("block_title").first().html();
            currSemester.setNumSemester(Integer.parseInt(
                    semesterTitle.substring(0, semesterTitle.indexOf(' '))));
            Elements classesHTML = currBlock.getElementsByTag("tbody")
                    .first().select("tr");
            for (int j = 0; j < classesHTML.size(); ++j) {
                classList.add(getClassFromHtml(classesHTML.get(j)));
            }
            currSemester.setClasses(classList);
            semesterList.add(currSemester);
        }
        return semesterList;
    }

    private ge.edu.freeuni.emis.emisapp.model.Class getClassFromHtml(Element classHTML) {
        Class currClass = new Class();
        Elements elems = classHTML.select("td");
        currClass.setClassName(elems.get(0).text());
        currClass.setLecturers(Arrays.asList(elems.get(1).text().split(",")));
        currClass.setNumCredits(Integer.parseInt(elems.get(2).text()));
        currClass.setStudentsGrade(new Grade(elems.get(4).text(),
                Double.parseDouble(elems.get(3).text())));


        return currClass;
    }

    @Override
    protected void onPostExecute(Object o) {
        notifySemesterListDownloaded((List<Semester>) o);
    }

    @Override
    public void registerListener(GradesLoadingListener listener) {
        this.listener = listener;
    }

    @Override
    public void unRegisterListener(GradesLoadingListener listener) {
        this.listener = null;
    }

    @Override
    public void notifySemesterListDownloaded(List<Semester> semesterList) {
        if (listener != null)
            listener.onGradesDownloaded(semesterList);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
