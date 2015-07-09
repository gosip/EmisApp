package ge.edu.freeuni.emis.emisapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.model.grading.Category;
import ge.edu.freeuni.emis.emisapp.model.grading.SingleDetailedGrade;

/**
 * Created by giorgi on 7/9/15.
 */
public class DetailedGradesListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Category> categories;
    private Map<String, List<SingleDetailedGrade>> grades;
    public DetailedGradesListAdapter(Context context, List<Category> categories,
                                     Map<String, List<SingleDetailedGrade>> grades) {
        this.context = context;
        this.categories = categories;
        this.grades = grades;
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return grades.get(categories.get(groupPosition).getCategoryIndicator()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return grades.get(categories.get(groupPosition).getCategoryIndicator()).get(childPosition);
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
        ParentViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ParentViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.detailed_grade_parent, parent, false);
            viewHolder.categoryText = (TextView) convertView.findViewById(R.id.category);
            viewHolder.scoreText = (TextView) convertView.findViewById(R.id.category_grade);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ParentViewHolder) convertView.getTag();
        }

        Category curr = (Category) getGroup(groupPosition);
        viewHolder.categoryText.setText(curr.getCategoryIndicator());
        viewHolder.scoreText.setText(curr.getCategoryScore() + " %");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.detailed_grade_child, parent, false);
            viewHolder.numText = (TextView) convertView.findViewById(R.id.number_text);
            viewHolder.scoreText = (TextView) convertView.findViewById(R.id.score_text);
            viewHolder.resultText = (TextView) convertView.findViewById(R.id.result_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }

        SingleDetailedGrade curr = (SingleDetailedGrade) getChild(groupPosition, childPosition);
        viewHolder.numText.setText("N:" + curr.getGradeNumber());
        viewHolder.scoreText.setText("score: " + curr.getScore() + "/" + curr.getMaxScore());
        viewHolder.resultText.setText("result: " + curr.getResult() + "/" + curr.getWeight());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class ParentViewHolder {
        TextView categoryText;
        TextView scoreText;
    }

    private static class ChildViewHolder {
        TextView numText;
        TextView scoreText;
        TextView resultText;
    }
}
