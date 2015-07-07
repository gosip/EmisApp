package ge.edu.freeuni.emis.emisapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.ui.DrawerItem;

/**
 * Created by giorgi on 7/7/15.
 */
public class DrawerListAdapter extends BaseAdapter {
    private Context context;
    private List<DrawerItem> drawerItemList;

    public DrawerListAdapter(Context context, List<DrawerItem> drawerItemList) {
        this.context = context;
        this.drawerItemList = drawerItemList;
    }

    @Override
    public int getCount() {
        return drawerItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return drawerItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.drawer_list_item, parent, false);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.drawer_icon);
            viewHolder.content = (TextView) convertView.findViewById(R.id.drawer_text_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DrawerItem currItem = (DrawerItem) getItem(position);
        viewHolder.icon.setImageResource(currItem.getImgResource());
        viewHolder.content.setText(currItem.getItemText());

        return convertView;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView content;
    }


}
