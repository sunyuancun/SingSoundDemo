package com.singsound.singsounddemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.singsound.singsounddemo.Config;
import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.base.BaseCloudActivity;
import com.singsound.singsounddemo.adapter.QuestionAnswerPagerAdapter;
import com.singsound.singsounddemo.adapter.WordPagerAdapter;
import com.singsound.singsounddemo.bean.QuestionAnswer;
import com.singsound.singsounddemo.bean.QuestionAnswerJsonBean;
import com.singsound.singsounddemo.utils.TitleBarUtil;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderDialog;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderUtils;
import com.tt.SingEngine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswer_OnlineCloudActivity extends BaseCloudActivity implements View.OnClickListener, AudioRecoderUtils.OnAudioStatusUpdateListener, View.OnTouchListener {

    SingEngine mSingEngine;

    private QuestionAnswerJsonBean mCurrentJson;
    private List<View> viewList = new ArrayList<>();

    RelativeLayout result_view;
    TextView zongfenview, wanzhengview, liuliview, zhunqueview;
    Button button_word, button_replay;
    LinearLayout line_wanzheng, line_zhunque, line_liuli;

    private AudioRecoderDialog mRecoderDialog;
    private AudioRecoderUtils mRecoderUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_question_answer__online_cloud);
        initTitle();
        initUI();
    }

    private void initTitle() {
        TitleBarUtil.initTitle(this, getIntent().getStringExtra("TITLE"));
    }


    ArrayList<QuestionAnswer> mQuestions = new ArrayList<>();
    ArrayList<QuestionAnswerJsonBean> mQuestionsJsons = new ArrayList<>();

    private void initQuestionAndAnswers() {

        String para = Config.QVA_PARA;

        //  题目1

        QuestionAnswer questionAnswer1 = new QuestionAnswer();
        questionAnswer1.setQuestion("你熟悉你家附近的道路吗？\n" + "_________________________\n" + "\n Generally, I am familiar with the roads around my house.");
        String[] keywords1 = {"familiar roads", "familiar way"};
        questionAnswer1.setKeyWords(keywords1);

        ArrayList<QuestionAnswer.Answer> answers1 = new ArrayList<QuestionAnswer.Answer>();
        QuestionAnswer.Answer answer11 = new QuestionAnswer.Answer();
        answer11.setAnswer("Are you familiar with the roads around your house?");
        answers1.add(answer11);
        QuestionAnswer.Answer answer21 = new QuestionAnswer.Answer();
        answer21.setAnswer("Are you familiar with the roads near your home?");
        answers1.add(answer21);
        QuestionAnswer.Answer answer31 = new QuestionAnswer.Answer();
        answer31.setAnswer("Are you familiar the way where near by your home?");
        answers1.add(answer31);
        QuestionAnswer.Answer answer41 = new QuestionAnswer.Answer();
        answer41.setAnswer("Are you familiar the roads nearby your home?");
        answers1.add(answer41);
//        QuestionAnswer.Answer answer51 = new QuestionAnswer.Answer();
//        answer51.setAnswer("will you replied him patiently when others ask you for the roads");
//        answers1.add(answer51);
        questionAnswer1.setAnswers(answers1);

        mQuestions.add(questionAnswer1);

        QuestionAnswerJsonBean questionAnswerJsonBean1 = new QuestionAnswerJsonBean();
        questionAnswerJsonBean1.setQuest_ans("Generally, I am familiar with the roads around my house.");
        questionAnswerJsonBean1.setPara(para);
        JSONArray lm1 = new JSONArray();
        JSONArray key1 = new JSONArray();
        try {

            JSONObject key11 = new JSONObject();
            key11.put("text", keywords1[0]);
            key1.put(key11);

            JSONObject key12 = new JSONObject();
            key12.put("text", keywords1[1]);
            key1.put(key12);

            JSONObject lm11 = new JSONObject();
            lm11.put("text", answer11.getAnswer());
            lm1.put(lm11);

            JSONObject lm12 = new JSONObject();
            lm12.put("text", answer21.getAnswer());
            lm1.put(lm12);

            JSONObject lm13 = new JSONObject();
            lm13.put("text", answer31.getAnswer());
            lm1.put(lm13);

            JSONObject lm14 = new JSONObject();
            lm14.put("text", answer41.getAnswer());
            lm1.put(lm14);

//            JSONObject lm15 = new JSONObject();
//            lm15.put("text", answer51.getAnswer());
//            lm1.put(lm15);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        questionAnswerJsonBean1.setLm(lm1);
        questionAnswerJsonBean1.setKey(key1);

        mQuestionsJsons.add(0, questionAnswerJsonBean1);

        //--------------------------------------------------------------
        //  题目2
        QuestionAnswer questionAnswer2 = new QuestionAnswer();
        questionAnswer2.setQuestion("What does he think of helping others?\n" + "\nTip: 高兴的。。 有用的" + "\n_________________________");
        String[] keywords2 = {"happy useful", "happy helpful", "good useful", "good helpful"};
        questionAnswer2.setKeyWords(keywords2);

        ArrayList<QuestionAnswer.Answer> answers2 = new ArrayList<QuestionAnswer.Answer>();
        QuestionAnswer.Answer answer12 = new QuestionAnswer.Answer();
        answer12.setAnswer(" feel happy and useful.");
        answers2.add(answer12);
        QuestionAnswer.Answer answer22 = new QuestionAnswer.Answer();
        answer22.setAnswer("He thinks that helping others will make him feel happy and useful. ");
        answers2.add(answer22);
        QuestionAnswer.Answer answer32 = new QuestionAnswer.Answer();
        answer32.setAnswer("he will feel good and useful.");
        answers2.add(answer32);
        QuestionAnswer.Answer answer42 = new QuestionAnswer.Answer();
        answer42.setAnswer("great and useful.");
        answers2.add(answer42);

        questionAnswer2.setAnswers(answers2);

        mQuestions.add(questionAnswer2);

        QuestionAnswerJsonBean questionAnswerJsonBean2 = new QuestionAnswerJsonBean();
        questionAnswerJsonBean2.setQuest_ans(" Helping others is a happy and useful thing.");
        questionAnswerJsonBean2.setPara(para);
        JSONArray lm2 = new JSONArray();
        JSONArray key2 = new JSONArray();
        try {

            JSONObject key11 = new JSONObject();
            key11.put("text", keywords2[0]);
            key2.put(key11);

            JSONObject key12 = new JSONObject();
            key12.put("text", keywords2[1]);
            key2.put(key12);

            JSONObject key13 = new JSONObject();
            key13.put("text", keywords2[2]);
            key2.put(key13);

            JSONObject key14 = new JSONObject();
            key14.put("text", keywords2[3]);
            key2.put(key14);

            JSONObject lm11 = new JSONObject();
            lm11.put("text", answer12.getAnswer());
            lm2.put(lm11);

            JSONObject lm12 = new JSONObject();
            lm12.put("text", answer22.getAnswer());
            lm2.put(lm12);

            JSONObject lm13 = new JSONObject();
            lm13.put("text", answer32.getAnswer());
            lm2.put(lm13);

            JSONObject lm14 = new JSONObject();
            lm14.put("text", answer42.getAnswer());
            lm2.put(lm14);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        questionAnswerJsonBean2.setLm(lm2);
        questionAnswerJsonBean2.setKey(key2);

        mQuestionsJsons.add(1, questionAnswerJsonBean2);

    }

    private void initUI() {

        mRecoderUtils = new AudioRecoderUtils();
        mRecoderUtils.setOnAudioStatusUpdateListener(this);

        mRecoderDialog = new AudioRecoderDialog(this);
        mRecoderDialog.setShowAlpha(0.98f);

        initQuestionAndAnswers();
        result_view = (RelativeLayout) findViewById(R.id.result_view);
        button_word = (Button) findViewById(R.id.bt_word);
        button_word.setOnTouchListener(this);
        button_replay = (Button) findViewById(R.id.bt_replay);
        button_replay.setOnClickListener(this);
        zongfenview = (TextView) findViewById(R.id.zongfenview);
        line_wanzheng = (LinearLayout) findViewById(R.id.line_wanzheng);
        line_liuli = (LinearLayout) findViewById(R.id.line_liuli);
        line_zhunque = (LinearLayout) findViewById(R.id.line_zhunque);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager1);
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < mQuestions.size(); i++) {
            View view = inflater.inflate(R.layout.item_question_answer_pager, null);
            viewList.add(view);
        }

        viewPager.setAdapter(new QuestionAnswerPagerAdapter(viewList, mQuestions));
        viewPager.setCurrentItem(0);
        mCurrentJson = mQuestionsJsons.get(0);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public void onUpdate(double db) {
        Log.e("-----------------", "update");
        if (null != mRecoderDialog) {
            mRecoderDialog.setLevel((int) db);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start();
                mRecoderUtils.startRecord();
                mRecoderDialog.showAtLocation(view, Gravity.CENTER, 0, 0);
                button_word.setBackgroundResource(R.drawable.shape_recoder_btn_recoding);
                return true;
            case MotionEvent.ACTION_UP:
                stop();
                mRecoderUtils.stopRecord();
                mRecoderDialog.dismiss();
                button_word.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                return true;
        }
        return false;
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentJson = mQuestionsJsons.get(position);
            result_view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void start() {
        try {
            JSONObject cfg = mSingEngine.buildStartJson();
            cfg.put("request",
                    cfg.getJSONObject("request")
                            .put("coreType", Config.TYPE_Question_answer)
                            .put("rank", 100)
                            .put("precision", 0.5)
                            .put("key", mCurrentJson.getKey())
                            .put("lm", mCurrentJson.getLm())
                            .put("quest_ans", mCurrentJson.getQuest_ans())
                            .put("para", mCurrentJson.getPara())
            );
            mSingEngine.setStartCfg(cfg);
            mSingEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        mSingEngine.stop();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.bt_replay) {
            if (mSingEngine != null) {
                mSingEngine.playback();
            }
        }
    }

    @Override
    protected void getInitSingEngine(SingEngine engine) {
        if (engine != null) {
            Log.e("-----------", "engine init  success");
            mSingEngine = engine;
        }
    }

    @Override
    protected void getResultFromServer(JSONObject result) {
        setResult(result);
    }

    private void setResult(final JSONObject result) {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    Log.d("--------result---------", result.toString());
                    if (result.has("result")) {
                        line_zhunque.setVisibility(View.GONE);
                        line_wanzheng.setVisibility(View.GONE);
                        line_liuli.setVisibility(View.GONE);
                        result_view.setVisibility(View.VISIBLE);
                        zongfenview.setText(result.getJSONObject("result").get("overall").toString());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
