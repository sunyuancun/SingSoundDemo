package com.singsound.singsounddemo.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.utils.StateColorUtils;
import com.singsound.singsounddemo.utils.TitleBar;
import com.singsound.singsounddemo.utils.TitleBarUtil;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateColorUtils.initStateColor(this);
        setContentView(R.layout.activity_about);
        initTitle();
    }

    private void initTitle() {
        TitleBarUtil.initTitle(this, getString(R.string.about));
    }
}
