package com.singsound.singsounddemo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.fragment.CloudFragment;
import com.singsound.singsounddemo.fragment.NativeFragment;
import com.singsound.singsounddemo.utils.TitleBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initTitle();
        initUI();
    }

    private void initTitle() {
        final TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30809f"));
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleBar.setTitle("先声教育语音评测演示");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);

        titleBar.setActionTextColor(Color.WHITE);
        titleBar.addAction(new TitleBar.TextAction("关于") {
            @Override
            public void performAction(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    private void initUI() {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.online_unselected, getResources().getString(R.string.online)).setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.offine_unselected, getResources().getString(R.string.offine)).setActiveColorResource(R.color.orange))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        fragments = getFragments();
        setDefaultFragment();
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.layFrame, cloudFragment);
        transaction.add(R.id.layFrame, nativeFragment);
        transaction.show(cloudFragment);
        transaction.hide(nativeFragment);
        transaction.commit();

    }

    CloudFragment cloudFragment;
    NativeFragment nativeFragment;

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        cloudFragment = new CloudFragment();
        nativeFragment = new NativeFragment();
        fragments.add(cloudFragment);
        fragments.add(nativeFragment);
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (!fragment.isVisible()) {
                    ft.show(fragment);
                } else {
                    return;
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.hide(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {
    }
}
