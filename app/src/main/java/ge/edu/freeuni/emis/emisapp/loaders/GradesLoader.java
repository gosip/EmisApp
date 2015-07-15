package ge.edu.freeuni.emis.emisapp.loaders;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.adapters.DetailedGradesListAdapter;
import ge.edu.freeuni.emis.emisapp.interfaces.GradesLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.GradesLoadingSubject;
import ge.edu.freeuni.emis.emisapp.loaders.util.JsonUtils;
import ge.edu.freeuni.emis.emisapp.loaders.util.JsoupUtils;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.model.grading.Category;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;
import ge.edu.freeuni.emis.emisapp.model.grading.SingleDetailedGrade;

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
        String link = elems.get(5).text();
        if (!link.equals("")) {
            String dataLid = classHTML.getElementsByTag("tr").first().attr("data-lid");
            // here would be code getting json files from web
            // instead mocking:
            if (dataLid.equals("805") || dataLid.equals("1292") || dataLid.equals("1296")) {
                try {
                    JSONObject json = JsonUtils.getJSONObject(dataLid, context);
                    parseJSON(json, currClass);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return currClass;
    }

    private void parseJSON(JSONObject json, Class cl) throws JSONException {
        if (json.isNull("comp")) return;
        JSONObject components = json.getJSONObject("comp");
        JSONObject eval = json.getJSONObject("est").getJSONObject("6076");
        Iterator<String> compIter = components.keys();
        while (compIter.hasNext()) {
            String categoryID = compIter.next();
            JSONObject currComp = components.getJSONObject(categoryID);
            JSONObject currCompEval = null;
            if (eval.has(categoryID)) {
                currCompEval = eval.getJSONObject(categoryID);
            }
            String categoryInd = currComp.getString("name");
            if (Arrays.asList(context.getResources().getStringArray(R.array.unused_cats))
                    .contains(categoryInd)) continue;
            double categoryScore = 0;
            if (currCompEval != null) {
                categoryScore = currCompEval.getDouble("shedegi");
            }
            int numSubComps = Integer.parseInt(currComp.getString("count"));
            JSONArray data = currComp.getJSONArray("data");
            for (int i = 1; i <= numSubComps; ++i) {
                JSONObject pair = data.getJSONObject(i - 1);
                double weight = Double.parseDouble(pair.getString("xvedriti_cona"));
                double maxScore = Double.parseDouble(pair.getString("max_shefaseba"));
                String gradeNumber = Integer.toString(i);
                double result = 0;
                double score = 0;
                if (currCompEval != null && numSubComps > 0) {
                    JSONObject currEval = currCompEval.getJSONObject(gradeNumber);
                    result = currEval.getDouble("shedegi");
                    score = currEval.getDouble("val");
                }
                SingleDetailedGrade detailedGrade = new SingleDetailedGrade(
                        i, score, maxScore, weight, result);
                cl.addDetailedGrade(categoryInd, detailedGrade, categoryScore);
            }
        }
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
