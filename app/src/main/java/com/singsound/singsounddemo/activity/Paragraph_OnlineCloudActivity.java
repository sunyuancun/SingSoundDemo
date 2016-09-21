package com.singsound.singsounddemo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.base.BaseCloudActivity;
import com.singsound.singsounddemo.utils.TitleBarUtil;
import com.tt.SingEngine;

import org.json.JSONObject;

public class Paragraph_OnlineCloudActivity extends BaseCloudActivity {

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
    }

    @Override
    protected void getInitSingEngine(SingEngine engine) {

    }

    @Override
    protected void getResultFromServer(JSONObject result) {

    }
}
