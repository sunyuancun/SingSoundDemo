package com.singsound.singsounddemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.bean.QuestionAnswer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2016/9/5.
 */
public class QuestionAnswerPagerAdapter extends PagerAdapter {

    List<View> viewList;
    ArrayList<QuestionAnswer> questions;

    public QuestionAnswerPagerAdapter(List<View> viewList, ArrayList<QuestionAnswer> questions) {
        this.viewList = viewList;
        this.questions = questions;
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
        QuestionAnswer qa = questions.get(position);
        TextView tv_position = (TextView) v.findViewById(R.id.position);
        TextView question_title = (TextView) v.findViewById(R.id.question_title);
//        TextView result_tv = (TextView) v.findViewById(R.id.result_tv);
        tv_position.setText(position + 1 + "/" + questions.size());
        question_title.setText(qa.getQuestion());
        container.addView(v, position);
        return v;
    }
}
