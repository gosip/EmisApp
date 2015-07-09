package ge.edu.freeuni.emis.emisapp.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;

/**
 * Created by giorgi on 7/7/15.
 */
public class SemesterListAdapter extends BaseExpandableListAdapter{
    private List<Semester> semesterList;
    private Context context;

    public void setSemesterList(List<Semester> semesterList) {
        this.semesterList = semesterList;
        notifyDataSetChanged();
    }

    public SemesterListAdapter(Context context, List<Semester> semesterList) {
        this.context = context;
        this.semesterList = semesterList;
    }

    @Override
    public int getGroupCount() {
        return semesterList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return semesterList.get(groupPosition).getClasses().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return semesterList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return semesterList.get(groupPosition).getClasses().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.semester_list_item, parent, false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.semester_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Semester currSemester = (Semester) getGroup(groupPosition);
        viewHolder.textView.setText("semester " + currSemester.getNumSemester());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.class_list_item, parent, false);
            viewHolder.cl = (TextView) convertView.findViewById(R.id.class_name);
            viewHolder.grd = (TextView) convertView.findViewById(R.id.class_grade);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }

        Class currClass = (Class) getChild(groupPosition, childPosition);
        viewHolder.cl.setText(currClass.getClassName());
        viewHolder.grd.setText(currClass.getStudentsGrade().getGradeIndicator()
                + " - " + currClass.getStudentsGrade().getScore());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewHolder{
        TextView textView;
    }

    private static class ChildViewHolder{
        TextView cl;
        TextView grd;
    }
}
