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

import com.singsound.singsounddemo.Config;
import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.base.BaseCloudActivity;
import com.singsound.singsounddemo.adapter.WordPagerAdapter;
import com.singsound.singsounddemo.utils.TitleBarUtil;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderDialog;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderUtils;
import com.tt.SingEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sentence_OnlineCloudActivity extends BaseCloudActivity implements View.OnClickListener, AudioRecoderUtils.OnAudioStatusUpdateListener, View.OnTouchListener {

    SingEngine mSingEngine;

    private String mCurrentSentence = "";
    private String[] sentences = {"Tom began to learn cooking when he was six years old.", " He prayed for God to rescue him, and every day he scanned the horizon for help, but none seemed forthcoming.", " No matter what you may buy, you might think those products were made in those countries."};
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
        setContentView(R.layout.activity_sentence__online_cloud);
        initTitle();
        initUI();
    }

    private void initTitle() {
        TitleBarUtil.initTitle(this, getIntent().getStringExtra("TITLE"));
    }

    private void initUI() {

        mRecoderUtils = new AudioRecoderUtils();
        mRecoderUtils.setOnAudioStatusUpdateListener(this);

        mRecoderDialog = new AudioRecoderDialog(this);
        mRecoderDialog.setShowAlpha(0.98f);

        result_view = (RelativeLayout) findViewById(R.id.result_view);
        button_word = (Button) findViewById(R.id.bt_word);
        button_word.setOnTouchListener(this);
        button_replay = (Button) findViewById(R.id.bt_replay);
        button_replay.setOnClickListener(this);
        zongfenview = (TextView) findViewById(R.id.zongfenview);
        wanzhengview = (TextView) findViewById(R.id.wanzhengview);
        liuliview = (TextView) findViewById(R.id.liuliview);
        zhunqueview = (TextView) findViewById(R.id.zhunqueview);
        line_wanzheng = (LinearLayout) findViewById(R.id.line_wanzheng);
        line_liuli = (LinearLayout) findViewById(R.id.line_liuli);
        line_zhunque = (LinearLayout) findViewById(R.id.line_zhunque);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager1);
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < sentences.length; i++) {
            View view = inflater.inflate(R.layout.item_words_pager, null);
            viewList.add(view);
        }

        viewPager.setAdapter(new WordPagerAdapter(viewList, sentences));
        viewPager.setCurrentItem(0);
        mCurrentSentence = sentences[0];
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
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
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.bt_replay) {
            if (mSingEngine != null) {
                mSingEngine.playback();
            }
        }
    }

    @Override
    public void onUpdate(double db) {
        Log.e("-----------------", "update");
        if (null != mRecoderDialog) {
            mRecoderDialog.setLevel((int) db);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentSentence = sentences[position];
            result_view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

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
                        Log.d("--------result---------", result.getJSONObject("result").get("overall").toString());
                        result_view.setVisibility(View.VISIBLE);
                        zongfenview.setText(result.getJSONObject("result").get("overall").toString());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //完整度
                try {
                    if (result.has("result")) {
                        result_view.setVisibility(View.VISIBLE);

                        JSONObject result_JsonObject = result.optJSONObject("result");
                        Object integrity = null;
                        if (result_JsonObject != null) {
                            integrity = result_JsonObject.opt("integrity");
                        }

                        if (integrity != null) {
                            line_wanzheng.setVisibility(View.VISIBLE);
                            wanzhengview.setText(String.valueOf(integrity));
                        } else {
                            line_wanzheng.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //准确度
                try {
                    if (result.has("result")) {
                        result_view.setVisibility(View.VISIBLE);

                        JSONObject result_JsonObject = result.optJSONObject("result");
                        Object pron = null;
                        if (result_JsonObject != null) {
                            pron = result_JsonObject.opt("pron");
                        }

                        if (pron != null) {
                            line_zhunque.setVisibility(View.VISIBLE);
                            zhunqueview.setText(String.valueOf(pron));
                        } else {
                            line_zhunque.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //流利度
                try {
                    if (result.has("result")) {
                        result_view.setVisibility(View.VISIBLE);

                        JSONObject result_JsonObject = result.optJSONObject("result");
                        Object fluency = null;
                        if (result_JsonObject != null) {
                            fluency = result_JsonObject.opt("fluency");
                        }

                        if (fluency != null) {
                            JSONObject fluency_obj = (JSONObject) fluency;
                            Object fluency_overall = fluency_obj.opt("overall");

                            if (fluency_overall != null) {
                                line_liuli.setVisibility(View.VISIBLE);
                                liuliview.setText(String.valueOf(fluency_overall));
                            } else {
                                line_liuli.setVisibility(View.GONE);
                            }


                        } else {
                            line_liuli.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void start() {
        try {
            JSONObject cfg = mSingEngine.buildStartJson();
            cfg.put("request",
                    cfg.getJSONObject("request")
                            .put("coreType", Config.TYPE_sent)
                            .put("refText", mCurrentSentence)
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

}
