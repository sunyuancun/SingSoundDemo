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
import com.singsound.singsounddemo.activity.Sentence_NativeActivity;
import com.singsound.singsounddemo.activity.Sentence_OnlineCloudActivity;
import com.singsound.singsounddemo.bean.SentenceDetail;
import com.singsound.singsounddemo.utils.multiaction_textview.InputObject;
import com.singsound.singsounddemo.utils.multiaction_textview.MultiActionTextView;

import java.util.List;

/**
 * Created by wang on 2016/8/31.
 */
public class WordPagerAdapter extends PagerAdapter implements Sentence_OnlineCloudActivity.UpdateOnlineColorCallBack, Sentence_NativeActivity.UpdateOffineColorCallBack {

    List<View> viewList;
    String[] words;

    public WordPagerAdapter(List<View> viewList, String[] words) {
        this.viewList = viewList;
        this.words = words;
        Sentence_OnlineCloudActivity.mUpdateOnlineColorCallBack = this;
        Sentence_NativeActivity.mUpdateOffineColorCallBack = this;
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

    @Override
    public void UpdateOnlineColor(Context context, int position, List<SentenceDetail> list) {
        updateSentenceColor(context, position, list);
    }

    @Override
    public void UpdateOffineColor(Context context, int position, List<SentenceDetail> list) {
        updateSentenceColor(context, position, list);
    }

    private void updateSentenceColor(Context context, int position, List<SentenceDetail> list) {
        View v = viewList.get(position);
        TextView tv_word = (TextView) v.findViewById(R.id.word);
        int startSpan = 0;
        int endSpan = 0;
        String str = "";
        double s_sore;
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        for (int i = 0; i < list.size(); i++) {
            startSpan = str.length();
            str = str + list.get(i).getCharX() + " ";
            s_sore = list.get(i).getScore();
            endSpan = str.length();
            stringBuilder.append(list.get(i).getCharX() + " ");

            InputObject nameClick = new InputObject();
            nameClick.setStartSpan(startSpan);
            nameClick.setEndSpan(endSpan);
            nameClick.setStringBuilder(stringBuilder);
            if (s_sore < 50)
                nameClick.setTextColor(context.getResources().getColor(R.color.text_red));
            else if (s_sore >= 50 && s_sore < 70)
                nameClick.setTextColor(context.getResources().getColor(R.color.text_yellow));
            else if (s_sore >= 70 && s_sore <= 100)
                nameClick.setTextColor(context.getResources().getColor(R.color.text_green));
            else
                nameClick.setTextColor(context.getResources().getColor(R.color.text_red));
            MultiActionTextView.addActionOnTextViewWithoutLink(nameClick);
            MultiActionTextView.setSpannableText(tv_word,
                    stringBuilder, Color.RED);

        }
    }

}

