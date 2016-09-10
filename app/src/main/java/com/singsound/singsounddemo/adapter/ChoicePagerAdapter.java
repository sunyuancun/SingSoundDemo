package com.singsound.singsounddemo.adapter;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.Choice_OnlineCloudActivity;
import com.singsound.singsounddemo.bean.ChoiceAnswer;
import com.singsound.singsounddemo.bean.ChoiceExam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2016/9/2.
 */
public class ChoicePagerAdapter extends PagerAdapter implements Choice_OnlineCloudActivity.ServerResultCallBack {

    List<View> viewList;
    List<ChoiceExam> examLists;


    public ChoicePagerAdapter(List<View> viewList, List<ChoiceExam> examLists) {
        Choice_OnlineCloudActivity.mServerResultCallBack = this;
        this.viewList = viewList;
        this.examLists = examLists;
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
        ChoiceExam exam = examLists.get(position);

        TextView tv_position = (TextView) v.findViewById(R.id.position);
        TextView tv_title = (TextView) v.findViewById(R.id.title);
        ImageView pic = (ImageView) v.findViewById(R.id.pic);
        TextView answer_A = (TextView) v.findViewById(R.id.answer_A);
        TextView answer_B = (TextView) v.findViewById(R.id.answer_B);
        TextView answer_C = (TextView) v.findViewById(R.id.answer_C);
        TextView answer_D = (TextView) v.findViewById(R.id.answer_D);

        if (exam.getType().equals(Choice_OnlineCloudActivity.TEXT_TYPE)) {
            pic.setVisibility(View.GONE);
        } else {
            pic.setVisibility(View.VISIBLE);
        }

        tv_position.setText(position + 1 + "/" + examLists.size());
        tv_title.setText(exam.getTitle());

        ArrayList<ChoiceAnswer> answers = exam.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            switch (i) {
                case 0:
                    answer_A.setText("A : " + answers.get(i).getText());
                    break;
                case 1:
                    answer_B.setText("B : " + answers.get(i).getText());
                    break;
                case 2:
                    answer_C.setText("C : " + answers.get(i).getText());
                    break;
                case 3:
                    answer_D.setText("D : " + answers.get(i).getText());
                    break;
            }
        }
        container.addView(v, position);
        return v;
    }

    @Override
    public void updatePositionViewUI(int position, Boolean rightOrNot) {

        View v = viewList.get(position);
        TextView tv_position = (TextView) v.findViewById(R.id.position);
        TextView tv_title = (TextView) v.findViewById(R.id.title);
        ImageView pic = (ImageView) v.findViewById(R.id.pic);
        TextView answer_A = (TextView) v.findViewById(R.id.answer_A);
        TextView answer_B = (TextView) v.findViewById(R.id.answer_B);
        TextView answer_C = (TextView) v.findViewById(R.id.answer_C);
        TextView answer_D = (TextView) v.findViewById(R.id.answer_D);

        ChoiceExam exam = examLists.get(position);
        ArrayList<ChoiceAnswer> answers = exam.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            switch (i) {
                case 0:
                    answer_A.setText("A : " + answers.get(i).getText());
                    if (answers.get(i).getRight()) {
                        if (rightOrNot) {
                            answer_A.setTextColor(Color.parseColor("#3bb2d7"));
                        } else {
                            answer_A.setTextColor(Color.parseColor("#FF4081"));
                        }
                    }
                    break;
                case 1:
                    answer_B.setText("B : " + answers.get(i).getText());
                    if (answers.get(i).getRight()) {
                        if (rightOrNot) {
                            answer_B.setTextColor(Color.parseColor("#3bb2d7"));
                        } else {
                            answer_B.setTextColor(Color.parseColor("#FF4081"));
                        }
                    }
                    break;
                case 2:
                    answer_C.setText("C : " + answers.get(i).getText());
                    if (answers.get(i).getRight()) {
                        if (rightOrNot) {
                            answer_C.setTextColor(Color.parseColor("#3bb2d7"));
                        } else {
                            answer_C.setTextColor(Color.parseColor("#FF4081"));
                        }
                    }
                    break;
                case 3:
                    answer_D.setText("D : " + answers.get(i).getText());
                    if (answers.get(i).getRight()) {
                        if (rightOrNot) {
                            answer_D.setTextColor(Color.parseColor("#239a13"));
                        } else {
                            answer_D.setTextColor(Color.parseColor("#FF4081"));
                        }
                    }
                    break;
            }
        }
    }
}
