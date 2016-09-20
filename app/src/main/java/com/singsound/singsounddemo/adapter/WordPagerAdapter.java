package com.singsound.singsounddemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.Sentence_OnlineCloudActivity;
import com.singsound.singsounddemo.bean.SentenceDetail;

import java.util.List;

/**
 * Created by wang on 2016/8/31.
 */
public class WordPagerAdapter extends PagerAdapter implements Sentence_OnlineCloudActivity.UpdateOnlineSentenceCallBack {

    List<View> viewList;
    String[] words;

    public WordPagerAdapter(List<View> viewList, String[] words) {
        this.viewList = viewList;
        this.words = words;
        Sentence_OnlineCloudActivity.mUpdateOnlineSentenceCallBack = this;
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


    //// TODO: 2016/9/20   有bug     index   of 
    @Override
    public void UpdateOnlineSentence(Context context, int position, String[] sentences, List<SentenceDetail> list) {
        View v = viewList.get(position);
        TextView tv_position = (TextView) v.findViewById(R.id.position);
        String s = sentences[position];
        SpannableStringBuilder style = new SpannableStringBuilder(s);
        int last_start = 0;
        for (SentenceDetail element : list) {
            double s_sore = element.getScore();
            String s_char = element.getCharX();

            int bstart = s.indexOf(s_char);
            int bend = bstart + s_char.length();

            if (s_sore < 50)
                style.setSpan(new ForegroundColorSpan(Color.RED), bstart, bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            else if (s_sore >= 50 && s_sore < 70)
                style.setSpan(new ForegroundColorSpan(Color.YELLOW), bstart, bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            else if (s_sore >= 70 && s_sore < 100)
                style.setSpan(new ForegroundColorSpan(Color.GREEN), bstart, bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        tv_position.setText(style);
    }
}

