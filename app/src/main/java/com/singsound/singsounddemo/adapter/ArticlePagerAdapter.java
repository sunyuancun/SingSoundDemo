package com.singsound.singsounddemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.singsound.singsounddemo.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wang on 2016/9/1.
 */
public class ArticlePagerAdapter extends PagerAdapter {
    List<View> viewList;
    List<HashMap> dataList;

    public ArticlePagerAdapter(List<View> viewList, List<HashMap> dataList) {
        this.viewList = viewList;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = viewList.get(position);
        HashMap map = dataList.get(position);
        TextView tv_position = (TextView) v.findViewById(R.id.position);
        final TextView tv_word = (TextView) v.findViewById(R.id.detail);
        TextView click_tv = (TextView) v.findViewById(R.id.click_tv);
        TextView tv_title = (TextView) v.findViewById(R.id.title);
        tv_position.setText(position + 1 + "/" + viewList.size());
        tv_word.setText((String) map.get("answer"));
        tv_title.setText((String) map.get("title"));
        click_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_word.getVisibility() == View.VISIBLE) {
                    tv_word.setVisibility(View.INVISIBLE);
                } else {
                    tv_word.setVisibility(View.VISIBLE);
                }
            }
        });

        container.addView(v, position);
        return v;
    }

}
