package com.singsound.singsounddemo.bean;

import java.util.ArrayList;

/**
 * Created by wang on 2016/9/2.
 */
public class ChoiceExam {

    //看图  文字
    String type;
    //title
    String title;

    ArrayList<ChoiceAnswer> answers;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ChoiceAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<ChoiceAnswer> answers) {
        this.answers = answers;
    }
}
