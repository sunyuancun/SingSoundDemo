package com.singsound.singsounddemo.activity;

import android.app.Activity;
import android.content.Context;
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

import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.base.BaseCloudActivity;
import com.singsound.singsounddemo.adapter.ParagraphPagerAdapter;
import com.singsound.singsounddemo.adapter.WordPagerAdapter;
import com.singsound.singsounddemo.bean.ParagraphDetail;
import com.singsound.singsounddemo.bean.SentenceDetail;
import com.singsound.singsounddemo.config.Config;
import com.singsound.singsounddemo.utils.TitleBarUtil;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderDialog;
import com.singsound.singsounddemo.utils.audiodialog.AudioRecoderUtils;
import com.tt.SingEngine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Paragraph_OnlineCloudActivity extends BaseCloudActivity implements AudioRecoderUtils.OnAudioStatusUpdateListener, View.OnTouchListener, View.OnClickListener {

    SingEngine mSingEngine;

    private String mCurrentPara = "";
    private List<View> viewList = new ArrayList<>();

    RelativeLayout result_view;
    TextView zongfenview, wanzhengview, liuliview, zhunqueview;
    Button button_word, button_replay;
    LinearLayout line_wanzheng, line_zhunque, line_liuli;

    private AudioRecoderDialog mRecoderDialog;
    private AudioRecoderUtils mRecoderUtils;

    int mPosition = 0;
    int lastposition = 0;


    public static UpdateOnlineColorCallBack mUpdateOnlineColorCallBack;

    public interface UpdateOnlineColorCallBack {
        void UpdateOnlineColor(Context context, int position, List<ParagraphDetail> list);
    }

    public static PageScrollStateChangedCallBack mPageScrollStateChangedCallBack;

    public interface PageScrollStateChangedCallBack {
        void initpositionViewOnlineParagraph(int position);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_paragraph__online_cloud);
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
        for (int i = 0; i < Config.paragraphs.length; i++) {
            View view = inflater.inflate(R.layout.item_paragraphs_pager, null);
            viewList.add(view);
        }

        viewPager.setAdapter(new ParagraphPagerAdapter(viewList, Config.paragraphs));
        viewPager.setCurrentItem(0);
        mCurrentPara = Config.paragraphs[0];
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setOffscreenPageLimit(0);
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            lastposition = mPosition;
            mPageScrollStateChangedCallBack.initpositionViewOnlineParagraph(lastposition);

            mPosition = position;
            mCurrentPara = Config.paragraphs[position];
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

    private void start() {
        try {
            JSONObject cfg = mSingEngine.buildStartJson();
            cfg.put("request",
                    cfg.getJSONObject("request")
                            .put("coreType", Config.TYPE_Paragraph)
                            .put("refText", mCurrentPara)
                            .put("precision", 0.5)
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

                              //todo  流利度    和句子的json格式有区别   fluency
                              try {
                                  if (result.has("result")) {
                                      result_view.setVisibility(View.VISIBLE);

                                      JSONObject result_JsonObject = result.optJSONObject("result");
                                      Object fluency = null;
                                      if (result_JsonObject != null) {
                                          fluency = result_JsonObject.opt("fluency");
                                      }

                                      if (fluency != null) {
                                          line_liuli.setVisibility(View.VISIBLE);
                                          liuliview.setText(String.valueOf(fluency));
                                      } else {
                                          line_liuli.setVisibility(View.GONE);
                                      }

                                  } else {
                                      line_liuli.setVisibility(View.GONE);
                                  }
                              } catch (Exception e) {
                                  e.printStackTrace();
                              }

                              updateView(result);
                          }
                      }

        );
    }

    private void updateView(JSONObject result) {
        try {
            List<ParagraphDetail> paragraphDetailList = new ArrayList<>();
            JSONArray details = result.getJSONObject("result").getJSONArray("details");

            for (int i = 0; i < details.length(); i++) {
                JSONObject detail = (JSONObject) details.get(i);
                ParagraphDetail paragraphDetail = new ParagraphDetail();
                String charx = detail.optString("text");
                double score = detail.optDouble("score");
                paragraphDetail.setText(charx);
                paragraphDetail.setScore(score);
                paragraphDetailList.add(i, paragraphDetail);
            }
            mUpdateOnlineColorCallBack.UpdateOnlineColor(Paragraph_OnlineCloudActivity.this, mPosition, paragraphDetailList);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("---SentenceDetail---", "error    json  parson  error");
        }
    }


    @Override
    public void onUpdate(double db) {
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_replay) {
            if (mSingEngine != null) {
                mSingEngine.playback();
            }
        }
    }
}
