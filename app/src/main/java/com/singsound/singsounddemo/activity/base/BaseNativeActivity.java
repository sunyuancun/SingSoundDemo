package com.singsound.singsounddemo.activity.base;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import android.widget.Toast;

import com.singsound.singsounddemo.utils.StateColorUtils;
import com.tt.SingEngine;

import org.json.JSONObject;

public abstract class BaseNativeActivity extends Activity implements SingEngine.ResultListener {

    private Boolean initSingEngine = false;
    private SingEngine engine = null;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateColorUtils.initStateColor(this);
        initContentView(savedInstanceState);

        //兼容6.0权限管理
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
        } else {
            initEngine();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                initEngine();
            } else {
                // Permission Denied
            }
        }
    }

    ProgressDialog mProgressDialog;

    private void initEngine() {

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    // 1 获取引擎实例
                    engine = SingEngine.newInstance(BaseNativeActivity.this, BaseNativeActivity.this, null);
                    // 2. 设置引擎类型  cloud ：云端    native： 离线     auto： 云端优先，无网络状态native
                    engine.setServerType("native");
                    // 3 构建引擎初始化参数
                    JSONObject cfg_init = engine.buildInitJson();
                    engine.setNewCfg(cfg_init);
                    // 4 创建 对象   回调 OnReady（）；
                    engine.newEngine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (engine != null && initSingEngine) {
            engine.cancel();
            Log.e("-----------", "engine cancel  success");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (engine != null && initSingEngine) {
            engine.stop();
            engine.cancel();
            engine.delete();
            Log.e("-----------", "engine cancel  and delete");
        }
    }

    // 初始化UI，setContentView等
    protected abstract void initContentView(Bundle savedInstanceState);

    // 获取初始化的SingEngine
    protected abstract void getInitSingEngine(SingEngine engine);

    // 获取评测结果
    protected abstract void getResultFromServer(JSONObject result);

    @Override
    public void onBegin() {

    }

    @Override
    public void onResult(JSONObject result) {
        getResultFromServer(result);
    }


    @Override
    public void onEnd(int Code, String msg) {
        Log.d("----------------", "onEnd:[" + Code + "]" + msg);
        if (Code != 0 && engine != null && initSingEngine) {
//            engine.stop();
        }
    }

    @Override
    public void onUpdateVolume(int volume) {

    }

    @Override
    public void onVadTimeOut() {

    }

    @Override
    public void onRecordingBuffer(byte[] data) {

    }

    @Override
    public void onReady() {
        Log.e("--------------", "onReady");
        initSingEngine = true;
        getInitSingEngine(engine);
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
