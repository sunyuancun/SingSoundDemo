package com.singsound.singsounddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.singsound.singsounddemo.utils.NetWorkUtils;

/**
 * Created by wang on 2016/9/7.
 */
public class NetWorkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetWorkUtils.getInstance().isConnected(context)) {
            Toast.makeText(context, "网络连接成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "检查网络连接", Toast.LENGTH_SHORT).show();
            Log.e("-------------","sss");
        }
    }

}
