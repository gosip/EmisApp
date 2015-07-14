package ge.edu.freeuni.emis.emisapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.model.TranscriptRow;

/**
 * Created by giorgi on 7/14/15.
 */
public class BSTranscriptListAdapter extends BaseAdapter {
    private Context context;
    private List<TranscriptRow> list;
    public void setList(List<TranscriptRow> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public BSTranscriptListAdapter(Context context, List<TranscriptRow> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.transcript_list_item, parent, false);
            viewHolder.className = (TextView) convertView.findViewById(R.id.class_name);
            viewHolder.classCode = (TextView) convertView.findViewById(R.id.class_code);
            viewHolder.classSemester = (TextView) convertView.findViewById(R.id.semester);
            viewHolder.classGrade = (TextView) convertView.findViewById(R.id.grade);
            viewHolder.classCredits = (TextView) convertView.findViewById(R.id.credits);
            viewHolder.classScore = (TextView) convertView.findViewById(R.id.score);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TranscriptRow row = (TranscriptRow) getItem(position);
        viewHolder.className.setText(row.getClassName());
        viewHolder.classCode.setText(row.getClassCode());
        viewHolder.classSemester.setText(row.getSemesterName());
        viewHolder.classGrade.setText(row.getStudentsGrade().getGradeIndicator() +
                 " - " + row.getStudentsGrade().getScore());
        viewHolder.classCredits.setText(row.getCreditsEarned() + " / " + row.getNumCredits());
        viewHolder.classScore.setText("" + row.getScoreEarned());

        return convertView;
    }

    private static class ViewHolder {
        TextView className;
        TextView classCode;
        TextView classSemester;
        TextView classGrade;
        TextView classCredits;
        TextView classScore;
    }
}
