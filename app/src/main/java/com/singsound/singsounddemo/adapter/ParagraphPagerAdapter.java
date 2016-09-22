package com.singsound.singsounddemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.Paragraph_OnlineCloudActivity;
import com.singsound.singsounddemo.bean.ParagraphDetail;
import com.singsound.singsounddemo.utils.multiaction_textview.InputObject;
import com.singsound.singsounddemo.utils.multiaction_textview.MultiActionTextView;

import java.util.List;

/**
 * Created by wang on 2016/9/21.
 */
public class ParagraphPagerAdapter extends PagerAdapter implements Paragraph_OnlineCloudActivity.UpdateOnlineColorCallBack, Paragraph_OnlineCloudActivity.PageScrollStateChangedCallBack {

    List<View> viewList;
    String[] paragraphs;

    public ParagraphPagerAdapter(List<View> viewList, String[] paragraphs) {
        this.viewList = viewList;
        this.paragraphs = paragraphs;
        Paragraph_OnlineCloudActivity.mUpdateOnlineColorCallBack = this;
        Paragraph_OnlineCloudActivity.mPageScrollStateChangedCallBack = this;
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
        tv_position.setText(position + 1 + "/" + paragraphs.length);
        tv_word.setText(paragraphs[position]);
        container.addView(v, position);
        return v;
    }

    @Override
    public void UpdateOnlineColor(Context context, int position, List<ParagraphDetail> list) {
        View v = viewList.get(position);
        TextView tv_word = (TextView) v.findViewById(R.id.word);

        int startSpan = 0;
        int endSpan = 0;
        String str = "";
        double s_sore;
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        for (int i = 0; i < list.size(); i++) {
            startSpan = str.length();
            str = str + list.get(i).getText() + " ";
            s_sore = list.get(i).getScore();
            endSpan = str.length();
            stringBuilder.append(list.get(i).getText() + " ");

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

    @Override
    public void initpositionViewOnlineParagraph(int position) {
        View v = viewList.get(position);
        TextView tv_word = (TextView) v.findViewById(R.id.word);
        tv_word.setText(paragraphs[position]);
    }
}
