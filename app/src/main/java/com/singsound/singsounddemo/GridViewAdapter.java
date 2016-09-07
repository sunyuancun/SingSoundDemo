package com.singsound.singsounddemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2016/8/29.
 */
public class GridViewAdapter extends BaseAdapter {

    Activity activity;
    List<Map<String, Object>> list;
    LayoutInflater mInflator;

    public GridViewAdapter(Activity activity, List<Map<String, Object>> list) {
        this.activity = activity;
        this.list = list;
        mInflator = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = mInflator.inflate(R.layout.grid_item, null);
        }

        TextView title_view = (TextView) view.findViewById(R.id.text);
        ImageView pic_view = (ImageView) view.findViewById(R.id.image);

        Map<String, Object> map = list.get(i);
        title_view.setText((String) map.get("text"));
        pic_view.setImageResource((int) map.get("image"));

        return view;
    }
}
