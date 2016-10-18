package com.singsound.singsounddemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.fragment.CloudFragment;
import com.singsound.singsounddemo.fragment.NativeFragment;
import com.singsound.singsounddemo.utils.SPUtils;
import com.singsound.singsounddemo.utils.StateColorUtils;
import com.singsound.singsounddemo.utils.TitleBar;
import com.singsound.singsounddemo.utils.tabview.TabView;
import com.singsound.singsounddemo.utils.tabview.TabViewChild;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int savedPosition = 0;
    CloudFragment cloudFragment;
    NativeFragment nativeFragment;
    TabView tabView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateColorUtils.initStateColor(this);
        setContentView(R.layout.activity_main1);
        initTitle();
        initUI();
    }

    private void initFragments() {
        cloudFragment = new CloudFragment();
        nativeFragment = new NativeFragment();
    }

    private void initTitle() {


        final TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setImmersive(true);
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

        titleBar.setLeftText("设置IP");
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedPosition = (int)SPUtils.get(MainActivity.this,SPUtils.SERVER_API_SELECTED_POSITION,0);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setSingleChoiceItems(R.array.server_api, savedPosition, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        // which表示点击的条目
                        Object checkedItem = lw.getAdapter().getItem(which);
                        SPUtils.put(MainActivity.this, SPUtils.SERVER_API, checkedItem.toString());
                        SPUtils.put(MainActivity.this, SPUtils.SERVER_API_SELECTED_POSITION, which);
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void initUI() {
        initFragments();
        tabView = (TabView) findViewById(R.id.tabView);
        tabView.setTabViewHeight(dip2px(52));
        //start add data
        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild01 = new TabViewChild(R.mipmap.online_unselected, R.mipmap.online_selected, "在线测评", cloudFragment);
        TabViewChild tabViewChild02 = new TabViewChild(R.mipmap.offine_unselected, R.mipmap.offine_selected, "离线测评", nativeFragment);
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());
        tabView.setTabViewDefaultPosition(0);
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView currentImageIcon, TextView currentTextView) {
                tabView.setTabViewDefaultPosition(position);
            }
        });
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
