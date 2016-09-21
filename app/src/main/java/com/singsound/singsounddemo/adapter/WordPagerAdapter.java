package com.singsound.singsounddemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.Sentence_OnlineCloudActivity;
import com.singsound.singsounddemo.bean.SentenceDetail;
import com.singsound.singsounddemo.utils.multiaction_textview.InputObject;
import com.singsound.singsounddemo.utils.multiaction_textview.MultiActionTextView;

import java.util.List;

/**
 * Created by wang on 2016/8/31.
 */
public class WordPagerAdapter extends PagerAdapter {

    List<View> viewList;
    String[] words;

    public WordPagerAdapter(List<View> viewList, String[] words) {
        this.viewList = viewList;
        this.words = words;

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
        TextView tv_position = (TextView) v.findViewById(R.id.position);
        TextView tv_word = (TextView) v.findViewById(R.id.word);
        tv_position.setText(position + 1 + "/" + words.length);
        tv_word.setText(words[position]);
        container.addView(v, position);
        return v;
    }

}

