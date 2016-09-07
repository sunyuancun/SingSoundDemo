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
        Log.e("-----------------", "update");
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

    JSONArray jsonArray_pic_article1;
    JSONArray jsonArray_pic_article2;

    private void initUIAndData() {

        mRecoderUtils = new AudioRecoderUtils();
        mRecoderUtils.setOnAudioStatusUpdateListener(this);

        mRecoderDialog = new AudioRecoderDialog(this);
        mRecoderDialog.setShowAlpha(0.98f);

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("title", "关于篮球\n" +
                "要点：\n" +
                "1. 运动对人的健康非常有益。\n" +
                "2. 我最喜欢的运动是篮球，我经常在周末和朋友们在公园打篮球。\n" +
                "3. 我觉得打篮球是释放压力的最好方式，还可以带给我们许多乐趣。\n" +
                "4. 姚明是我最喜爱的篮球明星。\n" +
                "5. 我打算长大后成为一名篮球运动员，所以我现在要每天练习。");
        map1.put("answer", "As we all know, doing sports is good for our health, because it can keep us healthy. As far as am concerned, i like playing basketball, and i often play basketball in the park with my friends on weekends. i think this is the best way to discharge the pressure. At the same time, it can bring lots of fun to us. I like Yaoming very much, he is my favourite basketball star. He can play basketball very well. I hope i can become a basketball player when i grow up. Only in this way, I can make some contribution to my motherland. So I have to practise every day.");
        dataList.add(map1);

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("title", "说出下面的话：我和我最好的朋友相处的非常好，但是我们却各有不同。他比我更有趣，更外向，而我很严肃。他是运动型的，各种体育运动都很喜欢，而我更擅长于学习。");
        map2.put("answer", "My best friend and I get along with each other quite well. But we are so different. He is funnier, more outgoing than I am and i'm more serious. He is more athletic and likes to play all kinds of sports but I am smarter on study.");
        dataList.add(map2);


        try {

            JSONObject json11 = new JSONObject();
            json11.put("answer", 1.0);
            json11.put("text", "As we all know, doing sports is good for our health, because it can keep us healthy. As far as am concerned, i like playing basketball, and i often play basketball in the park with my friends on weekends. i think this is the best way to discharge the pressure. At the same time, it can bring lots of fun to us. I like Yaoming very much, he is my favourite basketball star. He can play basketball very well. I hope i can become a basketball player when i grow up. Only in this way, I can make some contribution to my motherland. So I have to practise every day.");

            JSONObject json12 = new JSONObject();
            json12.put("answer", 1.0);
            json12.put("text", "Taking exercise is good for our health. The basketball is the best sport which I like. I will go to the park for playing basketball with my friends on Sunday. I think the basketball serves the best way to release the pressure and find some pleasure at the same time. By playing basketball, we can relax our body and mind.\n" +
                    "Yaoming is one of my favourite basketball star, I also want to be a professional basketball player when i grow up! so i need to practise every day.");

            JSONObject json13 = new JSONObject();
            json13.put("answer", 1.0);
            json13.put("text", "It will be good for your health to do exercise. Among all the sports, I like basketball most. I always play basketball with my friends together on sunday. As one of the most important sports in the world, Playing basketball is the best way to release the pressure. it will give me a lot of fun. My favorite basketball star is yaoming, the famous player from China. i wish i will be a basketball player in the future.");

            JSONObject json14 = new JSONObject();
            json14.put("answer", 1.0);
            json14.put("text", "Sports are very good for our health. My favourite sport is basketball. I often play basketball in the park with my friends at weekends. I think playing basketball is the best way to release my tension. It also can bring us a lot of joy. Yaoming is my favourite basketball star. I intend to be a basketball player when I grow up, so from now on, I must practise playing basketball everyday.");

            JSONObject json15 = new JSONObject();
            json15.put("answer", 1.0);
            json15.put("text", "It is very good for our health to do some sports. Basketball is my favourite sport. At weekends, I often play basketball with my friends in the park. I think it is the best way to release my pressure. Also, I can feel more enjoyable from playing basketball. My favourite basketball star is Yaoming. I am going to practise playing basketball everyday so that I can become a basketball player when I grow up.");

            JSONObject json16 = new JSONObject();
            json16.put("answer", 1.0);
            json16.put("text", "Sports can make us become healthy. My favourite sport is basketball. I always ask some friends come to the park to play basketball with me at weekends. In my opinion, playing basketball is the best way of unwinding. It can also bring joy for us. Yaoming is my favourite basketball star. I have to do some exercise everyday, because I dream of becoming a basketball player in the future.");

            jsonArray_pic_article1 = new JSONArray();
            jsonArray_pic_article1.put(json11);
            jsonArray_pic_article1.put(json12);
            jsonArray_pic_article1.put(json13);
            jsonArray_pic_article1.put(json14);
            jsonArray_pic_article1.put(json15);
            jsonArray_pic_article1.put(json16);

            JSONObject json21 = new JSONObject();
            json21.put("answer", 1.0);
            json21.put("text", "My best friend and I get along with each other quite well. But we are so different. He is funnier, more outgoing than I am and i'm more serious. He is more athletic and likes to play all kinds of sports but I am smarter on study.");

            JSONObject json22 = new JSONObject();
            json22.put("answer", 1.0);
            json22.put("text", "My best friend and I get along with each other quite well. But we are so different. He is funnier, more outgoing than I am and i'm more serious. He is more athletic and likes to play all kinds of sports but I am good at  study.");

            JSONObject json23 = new JSONObject();
            json23.put("answer", 1.0);
            json23.put("text", "My best friend and I get along with each other quite well. But we are so different. He is funnier, more outgoing than I am and i'm more serious. He is more athletic . likes to play all kinds of sports but I am smarter on study.");

            jsonArray_pic_article2 = new JSONArray();
            jsonArray_pic_article2.put(json21);
            jsonArray_pic_article2.put(json22);
            jsonArray_pic_article2.put(json23);


        } catch (Exception e) {
            e.printStackTrace();
        }

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
        mCurrentJSONArray = jsonArray_pic_article1;
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                mCurrentJSONArray = jsonArray_pic_article1;
            } else if (position == 1) {
                mCurrentJSONArray = jsonArray_pic_article2;
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

    private void setResult(final JSONObject result) {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    Log.d("--------result---------", result.toString());
                    if (result.has("result")) {
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
            JSONObject cfg = mSingEngine.buildStartJson();
            cfg.put("request",
                    cfg.getJSONObject("request")
                            .put("coreType", Config.TYPE_pic_article)
                            .put("rank", 100)
                            .put("precision", 0.5)
                            .put("lm", mCurrentJSONArray)
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
