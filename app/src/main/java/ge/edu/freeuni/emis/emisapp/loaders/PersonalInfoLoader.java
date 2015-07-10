package ge.edu.freeuni.emis.emisapp.loaders;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.renderscript.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.interfaces.PersonalInfoLoadingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.PersonalInfoLoadingSubject;
import ge.edu.freeuni.emis.emisapp.loaders.util.JsoupUtils;
import ge.edu.freeuni.emis.emisapp.model.Student;

public class PersonalInfoLoader extends AsyncTask
        implements PersonalInfoLoadingSubject{

    private PersonalInfoLoadingListener listener = null;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Document doc = JsoupUtils.getDocumentFromFile("student_info.html", context);
        String[] nodes = doc.select("div.st_user_right").get(0).text().split("\\s+");

        Student ret = buildStudent(nodes);
        nodes = doc.select("td").get(0).text().split("\\s+");
        StringBuilder builder = new StringBuilder();
        for (String node : nodes) {
            if (node.equals("სურათის"))
                break;
            builder.append(node);
            builder.append(" ");
        }

        ret.setStudentName(builder.toString());

        return ret;
    }

    private Student buildStudent(String[] nodes) {
        Student ret = new Student();
        for (int i = 0; i < nodes.length; i++) {
            String node = nodes[i];
            if (node.indexOf(":") > -1) {
                addField(nodes, i, ret);
            }
        }
        return ret;
    }

    private void addField(String[] nodes, int currKeyIdx, Student student) {
        String currNode = nodes[currKeyIdx];
        if (currNode.equals("სპეციალობა:")) {
            int nextIdx = findNextKeyIdx(nodes, currKeyIdx);
            student.setMajor(currValue(nodes, currKeyIdx, nextIdx));
        } else if (currNode.equals("საფეხური:")) {
            student.setExpectedDegree(nodes[currKeyIdx + 1]);
        } else if (currNode.equals("სემესტრი:")) {
            student.setCurrSemester(Integer.parseInt(nodes[currKeyIdx + 1]));
        } else if (currNode.equals("სქესი:")) {
            student.setGender(nodes[currKeyIdx + 1]);
        } else if (currNode.equals("ეროვნება:")) {
            student.setNationality(nodes[currKeyIdx + 1]);
        } else if (currNode.equals("თარიღი:")) {
            student.setBirthDate(nodes[currKeyIdx + 1]);
        } else if (currNode.equals("ადგილი:")) {
            int nextIdx = findNextKeyIdx(nodes, currKeyIdx);
            student.setAddress(currValue(nodes, currKeyIdx, nextIdx));
        } else if (currNode.equals("სტატუსი:")) {
            student.setStatus(nodes[currKeyIdx + 1]);
        } else if (currNode.equals("სკოლა:")) {
            int nextIdx = findNextKeyIdx(nodes, currKeyIdx) - 1;
            student.setSchoolName(currValue(nodes, currKeyIdx, nextIdx));
        } else if (currNode.equals("კრედიტები:")) {
            student.setNumCredits(Integer.parseInt(nodes[currKeyIdx + 1]));
        } else if (currNode.equals("GPA:")) {
            student.setGPA(Double.parseDouble(nodes[currKeyIdx + 1]));
        }
    }

    private int findNextKeyIdx(String[] nodes, int currKeyIdx) {
        int ret = -1;
        for (int i = currKeyIdx + 1; i < nodes.length; i++) {
            String node = nodes[i];
            if(node.indexOf(":") > -1){
                ret = i;
                break;
            }
        }
        return ret;
    }

    private String currValue(String[] nodes, int currIdx, int nextIdx) {
        StringBuilder builder = new StringBuilder();

        for (int i = currIdx + 1; i < nextIdx; i++) {
            String node = nodes[i];
            builder.append(node);
            if (i < nextIdx - 1)
                builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    protected void onPostExecute(Object o) {
        notifyPersonalInfoDownloaded((Student)o);
    }

    @Override
    public void registerListener(PersonalInfoLoadingListener listener) {
        this.listener = listener;
    }

    @Override
    public void unRegisterListener(PersonalInfoLoadingListener listener) {
        this.listener = null;
    }

    @Override
    public void notifyPersonalInfoDownloaded(Student studentInfo) {
        if (this.listener != null)
           this.listener.onPersonalInfoDownloaded(studentInfo);
    }


}
