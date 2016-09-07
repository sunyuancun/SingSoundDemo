package com.singsound.singsounddemo.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.singsound.singsounddemo.R;

/**
 * Created by wang on 2016/8/30.
 */
public class TitleBarUtil {

    public static void initTitle(final Activity activity, String title) {
        final TitleBar titleBar = (TitleBar) activity.findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30809f"));
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftImageResource(R.mipmap.back_arrow);
//        titleBar.setLeftText("返回");
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        titleBar.setTitle(title);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
    }

}
