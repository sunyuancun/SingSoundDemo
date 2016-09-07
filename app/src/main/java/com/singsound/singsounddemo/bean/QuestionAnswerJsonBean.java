package com.singsound.singsounddemo.bean;

import org.json.JSONArray;

/**
 * Created by wang on 2016/9/5.
 */
public class QuestionAnswerJsonBean {

    JSONArray lm;

    JSONArray key;

    String quest_ans;

    String para;

    public JSONArray getLm() {
        return lm;
    }

    public void setLm(JSONArray lm) {
        this.lm = lm;
    }

    public JSONArray getKey() {
        return key;
    }

    public void setKey(JSONArray key) {
        this.key = key;
    }

    public String getQuest_ans() {
        return quest_ans;
    }

    public void setQuest_ans(String quest_ans) {
        this.quest_ans = quest_ans;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }
}
