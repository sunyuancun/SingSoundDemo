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

import com.singsound.singsounddemo.config.ArticleConfig;
import com.singsound.singsounddemo.config.Config;
import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.base.BaseCloudActivity;
import com.singsound.singsounddemo.adapter.ArticlePagerAdapter;
import com.singsound.singsounddemo.utils.TitleBarUtil;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderDialog;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderUtils;
import com.tt.SingEngine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Article_OnlineCloudActivity extends BaseCloudActivity implements View.OnClickListener, AudioRecoderUtils.OnAudioStatusUpdateListener, View.OnTouchListener {

    SingEngine mSingEngine;

    private JSONArray mCurrentJSONArray;
    private List<View> viewList = new ArrayList<>();
    private List<HashMap> dataList = new ArrayList<>();
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
        setContentView(R.layout.activity_article__online_cloud);
        initTitle();
        initUIAndData();
    }

    private void initTitle() {
        TitleBarUtil.initTitle(this, getIntent().getStringExtra("TITLE"));
    }


    private void initUIAndData() {

        mRecoderUtils = new AudioRecoderUtils();
        mRecoderUtils.setOnAudioStatusUpdateListener(this);

        mRecoderDialog = new AudioRecoderDialog(this);
        mRecoderDialog.setShowAlpha(0.98f);

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("title", ArticleConfig.titles[0]);
        map1.put("answer", ArticleConfig.biaozhun_ans[0]);
        dataList.add(map1);

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("title", ArticleConfig.titles[1]);
        map2.put("answer", ArticleConfig.biaozhun_ans[1]);
        dataList.add(map2);


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
        for (int i = 0; i < dataList.size(); i++) {
            View view = inflater.inflate(R.layout.item_article_pager, null);
            viewList.add(view);
        }

        viewPager.setAdapter(new ArticlePagerAdapter(viewList, dataList));
        viewPager.setCurrentItem(0);
        mCurrentJSONArray = ArticleConfig.getJsonArrayLm(0);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                mCurrentJSONArray = ArticleConfig.getJsonArrayLm(0);
            } else if (position == 1) {
                mCurrentJSONArray = ArticleConfig.getJsonArrayLm(1);
            }
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

    }

    private void setResult(final JSONObject result) {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    System.out.print(result.toString());
                    if (result.has("result")) {
                        Log.d("--------result---------", result.toString());
                        Log.d("--------result---------", result.getJSONObject("result").get("overall").toString());
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

    private void start() {
        try {
            JSONObject request = new JSONObject();
            request.put("coreType", Config.TYPE_pic_article)
                    .put("rank", 100)
                    .put("precision", 0.5)
                    .put("lm", mCurrentJSONArray);

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
