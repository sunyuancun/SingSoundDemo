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
import com.singsound.singsounddemo.activity.base.BaseNativeActivity;
import com.singsound.singsounddemo.adapter.WordPagerAdapter;
import com.singsound.singsounddemo.utils.TitleBarUtil;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderDialog;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderUtils;
import com.tt.SingEngine;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Word_NativeActivity extends BaseNativeActivity implements View.OnClickListener, AudioRecoderUtils.OnAudioStatusUpdateListener, View.OnTouchListener {

    SingEngine mSingEngine;

    private String mCurrentWord = "";
    private String[] words = {"familiar", "patience", "neighbourhood"};
    private List<View> viewList = new ArrayList<>();
    private int text_green_color, text_red_color, text_yellow_color;


    RelativeLayout result_view;
    TextView zongfenview, wanzhengview, liuliview, zhunqueview;
    Button button_word, button_replay;
    LinearLayout line_wanzheng, line_zhunque, line_liuli;

    private AudioRecoderDialog mRecoderDialog;
    private AudioRecoderUtils mRecoderUtils;


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("-----------------", "DOWN");
                button_word.setBackgroundResource(R.drawable.shape_recoder_btn_recoding);
                mRecoderUtils.startRecord();
                mRecoderDialog.showAtLocation(view, Gravity.CENTER, 0, 0);
                start();
                return true;

            case MotionEvent.ACTION_UP:
                Log.d("-----------------", "UP");
                button_word.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                mRecoderUtils.stopRecord();
                mRecoderDialog.dismiss();
                stop();
                return true;
        }
        return false;
    }

    @Override
    public void onUpdate(double db) {
        if (null != mRecoderDialog) {
            mRecoderDialog.setLevel((int) db);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_word__native);
        initTitle();
        initUI();
    }

    private void initTitle() {
        TitleBarUtil.initTitle(this, getIntent().getStringExtra("TITLE"));
    }

    private void initUI() {

        text_green_color = getResources().getColor(R.color.text_green);
        text_red_color = getResources().getColor(R.color.text_red);
        text_yellow_color = getResources().getColor(R.color.text_yellow);

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
        line_wanzheng = (LinearLayout) findViewById(R.id.line_wanzheng);
        line_liuli = (LinearLayout) findViewById(R.id.line_liuli);
        line_zhunque = (LinearLayout) findViewById(R.id.line_zhunque);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager1);
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < words.length; i++) {
            View view = inflater.inflate(R.layout.item_words_pager, null);
            viewList.add(view);
        }

        viewPager.setAdapter(new WordPagerAdapter(viewList, words));
        viewPager.setCurrentItem(0);
        mCurrentWord = words[0];
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentWord = words[position];
            result_view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("-----------------", "UP");
                button_word.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                mRecoderUtils.stopRecord();
                mRecoderDialog.dismiss();
            }
        });
    }

    private void setResult(final JSONObject result) {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    Log.d("--------result---------", result.toString());
                    if (result.has("result")) {
                        JSONObject result_JsonObject = result.optJSONObject("result");
                        Object overall = null;
                        if (result_JsonObject != null) {
                            overall = result_JsonObject.opt("overall");
                        }

                        showResultOnTextView(overall);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showResultOnTextView(Object overall) {
        try {
            if (overall != null) {
                result_view.setVisibility(View.VISIBLE);
                line_zhunque.setVisibility(View.GONE);
                line_wanzheng.setVisibility(View.GONE);
                line_liuli.setVisibility(View.GONE);
                zongfenview.setVisibility(View.VISIBLE);

                double overall_double = Double.parseDouble(String.valueOf(overall));
                if (overall_double < 50) {
                    zongfenview.setTextColor(text_red_color);
                } else if (overall_double >= 50 && overall_double < 70) {
                    zongfenview.setTextColor(text_yellow_color);
                } else if (overall_double >= 70 && overall_double <= 100) {
                    zongfenview.setTextColor(text_green_color);
                }
                zongfenview.setText(String.valueOf(overall));

            } else {
                zongfenview.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() {
        try {
            JSONObject request = new JSONObject();
            request.put("coreType", Config.TYPE_Word)
                    .put("refText", mCurrentWord)
                    .put("rank", 100);
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
