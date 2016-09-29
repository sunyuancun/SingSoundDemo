package com.singsound.singsounddemo.activity;

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

import com.singsound.singsounddemo.config.Config;
import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.base.BaseCloudActivity;
import com.singsound.singsounddemo.adapter.ChoicePagerAdapter;
import com.singsound.singsounddemo.bean.ChoiceAnswer;
import com.singsound.singsounddemo.bean.ChoiceExam;
import com.singsound.singsounddemo.utils.TitleBarUtil;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderDialog;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderUtils;
import com.tt.SingEngine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Choice_OnlineCloudActivity extends BaseCloudActivity implements View.OnClickListener, AudioRecoderUtils.OnAudioStatusUpdateListener, View.OnTouchListener {


    public static final String TEXT_TYPE = "TEXT";
    private static final String PIC_TYPE = "PIC";
    private List<View> viewList = new ArrayList<>();
    private List<ChoiceExam> examLists = new ArrayList<>();

    RelativeLayout result_view;
    TextView zongfenview, wanzhengview, liuliview, zhunqueview, tv_zongfen;
    Button button_word, button_replay;
    LinearLayout line_wanzheng, line_zhunque, line_liuli;
    ViewPager viewPager;

    private SingEngine mSingEngine;

    JSONArray jsonArray_current;
    int current_position;
    ArrayList<JSONArray> json_arrys = new ArrayList<>();

    private AudioRecoderDialog mRecoderDialog;
    private AudioRecoderUtils mRecoderUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choice__online_cloud);
        initTitle();
        initUI();
    }

    private void initUI() {

        mRecoderUtils = new AudioRecoderUtils();
        mRecoderUtils.setOnAudioStatusUpdateListener(this);

        mRecoderDialog = new AudioRecoderDialog(this);
        mRecoderDialog.setShowAlpha(0.98f);

        //init choiceExam

        ChoiceExam exam1 = new ChoiceExam();
        exam1.setType(TEXT_TYPE);
        exam1.setTitle("How many days a week?");

        ArrayList<ChoiceAnswer> answers1 = new ArrayList<>();

        ChoiceAnswer answer11 = new ChoiceAnswer();
        answer11.setText("one");
        answer11.setRight(false);
        answers1.add(answer11);

        ChoiceAnswer answer12 = new ChoiceAnswer();
        answer12.setText("two");
        answer12.setRight(false);
        answers1.add(answer12);

        ChoiceAnswer answer13 = new ChoiceAnswer();
        answer13.setText("seven");
        answer13.setRight(true);
        answers1.add(answer13);

        ChoiceAnswer answer14 = new ChoiceAnswer();
        answer14.setText("eight");
        answer14.setRight(false);
        answers1.add(answer14);

        exam1.setAnswers(answers1);
        examLists.add(exam1);

        try {

            JSONObject json1 = new JSONObject();
            json1.put("answer", 0.0);
            json1.put("text", "one");

            JSONObject json2 = new JSONObject();
            json2.put("answer", 2.0);
            json2.put("text", "seven");

            JSONObject json3 = new JSONObject();
            json3.put("answer", 0.0);
            json3.put("text", "two");

            JSONObject json4 = new JSONObject();
            json4.put("answer", 0.0);
            json4.put("text", "eight");

            JSONArray jsonArray_choc_0 = new JSONArray();
            jsonArray_choc_0.put(json1);
            jsonArray_choc_0.put(json2);
            jsonArray_choc_0.put(json3);
            jsonArray_choc_0.put(json4);
            json_arrys.add(0, jsonArray_choc_0);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //pic choice
        ChoiceExam exam2 = new ChoiceExam();
        exam2.setType(PIC_TYPE);
        exam2.setTitle("How do I go to school?");
        ArrayList<ChoiceAnswer> answers2 = new ArrayList<>();

        ChoiceAnswer answer21 = new ChoiceAnswer();
        answer21.setText("walk to school");
        answer21.setRight(false);
        answers2.add(answer21);

        ChoiceAnswer answer22 = new ChoiceAnswer();
        answer22.setText("by bike");
        answer22.setRight(true);
        answers2.add(answer22);

        ChoiceAnswer answer23 = new ChoiceAnswer();
        answer23.setText("by bus");
        answer23.setRight(false);
        answers2.add(answer23);

        ChoiceAnswer answer24 = new ChoiceAnswer();
        answer24.setText("by car");
        answer24.setRight(false);
        answers2.add(answer24);

        exam2.setAnswers(answers2);
        examLists.add(exam2);

        try {

            JSONObject json21 = new JSONObject();
            json21.put("answer", 0.0);
            json21.put("text", "walk to school");

            JSONObject json22 = new JSONObject();
            json22.put("answer", 2.0);
            json22.put("text", "by bike");

            JSONObject json23 = new JSONObject();
            json23.put("answer", 0.0);
            json23.put("text", "by bus");

            JSONObject json24 = new JSONObject();
            json24.put("answer", 0.0);
            json24.put("text", "by car");

            JSONArray jsonArray_choc_1 = new JSONArray();
            jsonArray_choc_1.put(json21);
            jsonArray_choc_1.put(json22);
            jsonArray_choc_1.put(json23);
            jsonArray_choc_1.put(json24);
            json_arrys.add(1, jsonArray_choc_1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        result_view = (RelativeLayout) findViewById(R.id.result_view);
        button_word = (Button) findViewById(R.id.bt_word);
        button_word.setOnTouchListener(this);
        button_replay = (Button) findViewById(R.id.bt_replay);
        button_replay.setOnClickListener(this);
        zongfenview = (TextView) findViewById(R.id.zongfenview);
        tv_zongfen = (TextView) findViewById(R.id.tv_zongfen);
        line_wanzheng = (LinearLayout) findViewById(R.id.line_wanzheng);
        line_liuli = (LinearLayout) findViewById(R.id.line_liuli);
        line_zhunque = (LinearLayout) findViewById(R.id.line_zhunque);

        viewPager = (ViewPager) findViewById(R.id.viewPager1);
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < examLists.size(); i++) {
            View view = inflater.inflate(R.layout.item_choice_pager, null);
            viewList.add(view);
        }
        viewPager.setAdapter(new ChoicePagerAdapter(viewList, examLists));
        viewPager.setCurrentItem(0);
        current_position = 0;
        jsonArray_current = json_arrys.get(0);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            result_view.setVisibility(View.INVISIBLE);
            jsonArray_current = json_arrys.get(position);
            current_position = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initTitle() {
        TitleBarUtil.initTitle(this, getIntent().getStringExtra("TITLE"));
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

    @Override
    protected void stopSingEngineSuccess() {

    }

    private void setResult(final JSONObject result) {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    Log.d("--------result---------", result.toString());
                    if (result.has("result")) {
                        int resultScore = (int) result.getJSONObject("result").get("overall");
                        line_zhunque.setVisibility(View.GONE);
                        line_wanzheng.setVisibility(View.GONE);
                        line_liuli.setVisibility(View.GONE);
                        result_view.setVisibility(View.VISIBLE);
                        tv_zongfen.setVisibility(View.GONE);
                        // todo 判断正确的结果的判断条件
                        if (resultScore == 2) {
                            mServerResultCallBack.updatePositionViewUI(current_position, true);
                            zongfenview.setBackgroundResource(R.mipmap.result_right);
                        } else {
                            mServerResultCallBack.updatePositionViewUI(current_position, false);
                            zongfenview.setBackgroundResource(R.mipmap.result_wrong);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static ServerResultCallBack mServerResultCallBack;

    public interface ServerResultCallBack {
        void updatePositionViewUI(int position, Boolean rightOrNot);
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

    @Override
    public void onUpdate(double db) {
        Log.e("-----------------", "update");
        if (null != mRecoderDialog) {
            mRecoderDialog.setLevel((int) db);
        }
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

    private void start() {
        try {
            JSONObject request = new JSONObject();
            request.put("coreType", Config.TYPE_choc)
                    .put("rank", 2)
                    .put("precision", 1)
                    .put("lm", jsonArray_current);
            JSONObject cfg = mSingEngine.buildStartJson(request);
            mSingEngine.setStartCfg(cfg);
            mSingEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        mSingEngine.stop();
    }

}
