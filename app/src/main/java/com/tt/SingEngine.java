package com.tt;

import android.content.Context;
import android.util.Log;

import com.xs.AIRecorder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fred on 16-06-21.
 */
public class SingEngine {
    /**
     * 回调接口
     */
    public interface ResultListener {
        /**
         * 录音开始时回调
         */
        public abstract void onBegin();

        /**
         * 测评结果返回
         *
         * @param result 测评结果json对象
         */
        public abstract void onResult(JSONObject result);

        /**
         * 结束时回调
         *
         * @param Code 错误代码，等于0时为正常结束
         * @param msg  错误消息
         */
        public abstract void onEnd(int Code, String msg);

        /**
         * 麦克风音量回调
         *
         * @param volume
         */
        public abstract void onUpdateVolume(final int volume);

        /**
         * vad 超时回调
         */
        public abstract void onVadTimeOut();

        /**
         * 录音数据回调
         *
         * @param data
         */
        public abstract void onRecordingBuffer(byte[] data);

        /**
         * 引擎初始化成功
         */
        public abstract void onReady();
    }

    public enum coreProvideType {
        CLOUD("cloud"), NATIVE("native"), AUTO("auto");
        private String value;

        private coreProvideType(String value) {
            this.setValue(value);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    ;

    public enum coreType {
        enWord("en.word.score"),
        enSent("en.sent.score");

        private String value;

        private coreType(String value) {
            this.setValue(value);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    ;


    private Context ct;

    private long engine = 0;

    private AIRecorder recorder = null;

    private ResultListener caller;

    private JSONObject newCfg = null;

    private JSONObject startCfg = null;

    private String local = null;

    private coreProvideType cpt = coreProvideType.CLOUD;

    private int vadBacktime = 3000;
    private int vadFronttime = 3000;

    private String wavPath = "";

    private static String native_res_path2 = "{"
            + "\"en.sent.score\":{\"resDirPath\": \"%s/resources/eval/bin/eng.snt.pydnn.16bit\"}"
            + ",\"en.word.score\":{\"resDirPath\": \"%s/resources/eval/bin/eng.wrd.pydnn.16bit\"}"
            + "}";

    private static String nativeResource = "resources.zip";

    public SingEngine(Context context) {
        ct = context;
    }

    public static SingEngine newInstance(Context context, ResultListener listener, String Resource) {
        SingEngine fragment = new SingEngine(context);
        fragment.setListener(listener);
        fragment.setLocal(Resource);
        return fragment;
    }

    public void setServerType(String type) {
        if (type.equals(coreProvideType.AUTO.getValue())) {
            cpt = coreProvideType.AUTO;
        } else if (type.equals(coreProvideType.NATIVE.getValue())) {
            cpt = coreProvideType.NATIVE;
        } else {
            cpt = coreProvideType.CLOUD;
        }
    }

    public String getVersion() {
        return "0.1.0";
    }

    public String getWavPath() {
        return wavPath;
    }

    public void wavWithOutHeader(Boolean isheader) {
        recorder.setFileheader(isheader);
    }

    public void setServerType(coreProvideType type) {
        cpt = type;
    }

    public void setLocal(String Resource) {
        local = Resource;
    }

    public void setVadBackTime(int time) {
        vadBacktime = time;
    }

    public void setVadFrontTime(int time) {
        vadFronttime = time;
    }

    public void setListener(ResultListener listenner) {
        caller = listenner;
    }

    private JSONObject buildNative() throws JSONException {
        if (local == null) {
            local = new String(AiUtil.unzipFile(ct, nativeResource).toString());
        }

        String res_path = String.format(native_res_path2, local, local);
        return new JSONObject(res_path);
    }

    public void setNewCfg(JSONObject cfg) {
        newCfg = cfg;
    }


    //   在这设置引擎服务器 api
    public JSONObject buildInitJson() throws JSONException {
        JSONObject cfg = new JSONObject();
        JSONObject cloud = new JSONObject();
        String svr = "ws://api.cloud.ssapi.cn:8080";
        cloud.put("enable", 1)
//                .put("server", svr)
                .put("server", "ws://120.92.133.98:8090")
                .put("connectTimeout", 20)
                .put("serverTimeout", 60);

        cfg.put("appKey", "1459219202000001")
                .put("secretKey", "3bc23f814868ebd5b61a71acde532abb")
//                .put("native", buildNative())
                .put("cloud", cloud);

        return cfg;
    }

    public void setStartCfg(JSONObject cfg) {
        startCfg = cfg;
    }

    public JSONObject buildStartJson() throws JSONException {
        JSONObject cfg = new JSONObject();

        JSONObject request = new JSONObject();
        request.put("coreType", coreType.enSent.getValue())
                .put("refText", "")
                .put("rank", 100);
        JSONObject audio = new JSONObject();
        audio.put("audioType", "wav")
                .put("sampleRate", 16000)
                .put("sampleBytes", 2)
                .put("channel", 1);
        cfg.put("coreProvideType", cpt.getValue())
                .put("app", new JSONObject("{\"userId\": \"guest\"}"))
                .put("audio", audio)
                .put("request", request);

        return cfg;
    }

    public void newEngine() throws JSONException {
        if (cpt != coreProvideType.CLOUD && !newCfg.has("native")) {
            newCfg.put("native", buildNative());
        }
        engine = SSound.ssound_new(newCfg.toString(), ct);
        if (engine == 0) caller.onEnd(60001, "please check param");

        recorder = AIRecorder.getInstance();
        caller.onReady();
    }

    public void newEngineByAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    newEngine();
                } catch (JSONException e) {
//                    e.printStackTrace();
                    caller.onEnd(60002, "param string error");
                }
            }
        }).start();
    }

    public int SSoundStart(byte[] rid) {

        ChooseServerType_If_AutoType();

        int r = SSound.ssound_start(engine, startCfg.toString(), rid, new SSound.ssound_callback() {
            @Override
            public int run(byte[] id, int type, byte[] data, int size) {
                if (type == SSound.SSOUND_MESSAGE_TYPE_JSON) {
                    final String result = new String(data, 0, size).trim();
                    try {
                        final JSONObject json = new JSONObject(result);
                        caller.onResult(json);
                        caller.onEnd(0, "");
                    } catch (JSONException e) {
                        caller.onEnd(70001, "server result string error: " + result);
                        e.printStackTrace();
                    }
                }
                return 0;
            }
        }, ct);
        if (r != 0) {
            stop();
        }

        return r;
    }

    private void ChooseServerType_If_AutoType() {
        try {
            if (cpt != coreProvideType.AUTO) {
                return;
            }

            if (!NetWorkUtil.getInstance().isConnected(ct))
                startCfg.put("coreProvideType", coreProvideType.NATIVE.getValue());

            else
                startCfg.put("coreProvideType", coreProvideType.CLOUD.getValue());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        log("------------------" + startCfg.optString("coreProvideType"));
    }

    public int SSoundFeed(byte[] data, int size) {
        int rr = SSound.ssound_feed(engine, data, size);
        if (rr != 0) {
            stop();
        }
        return rr;
    }

    public void start() {
        byte[] rid = new byte[64];
        if (SSoundStart(rid) != 0) return;
        int r;
        wavPath = AiUtil.getFilesDir(
                ct.getApplicationContext()).getPath()
                + "/record/" + new String(rid).trim() + ".wav";

        r = recorder.start(wavPath, new AIRecorder.Callback() {
            public void run(byte[] data, int size) {
                if (size > 0) {
                    int v = 0;
                    for (int i = 0; i < data.length; i++) {
                        v += data[i] * data[i];
                    }
                    double mean = v / (double) size;
                    double volume = 100 * Math.log10(mean);
                    caller.onUpdateVolume((int) volume);
                    caller.onRecordingBuffer(data);
                    int rr = SSound.ssound_feed(engine, data, size);
                    if (rr != 0) {
                        stop();
                    }
                } else if (size == -1) {
                    Log.d("vad:", "vad time callback");
                    caller.onVadTimeOut();
                }
            }
        }, vadBacktime, vadFronttime);
        if (r != 0) {
            caller.onEnd(70004, "AIRecorder start error");
        }

        caller.onBegin();
    }

    /**
     * 离线WAV
     *
     * @param wavName
     */
    public void startWithPCM(String wavName) {
        byte[] rid = new byte[64];
        if (SSoundStart(rid) != 0) return;
        int bytes, rv;
        byte[] buf = new byte[1024];

        InputStream fis;
        try {
            fis = ct.getAssets().open(wavName);
            while ((bytes = fis.read(buf, 0, 1024)) > 0) {
                if ((rv = SSound.ssound_feed(engine, buf, bytes)) != 0) {
                    break;
                }
            }

            fis.close();
        } catch (IOException e) {
            caller.onEnd(70011, "feed audio data fail");
        } finally {
        }

        stop();

    }

    /**
     * 离线 MP3 文件评测
     *
     * @param MP3Name
     */
    public void startWithLocalMP3(String MP3Name) {
        byte[] rid = new byte[64];
        if (SSoundStart(rid) != 0) return;

        InputStream fis;
        try {
            fis = ct.getAssets().open(MP3Name);
            int length = fis.available();
            byte[] buf_mp3 = new byte[length];
            fis.read(buf_mp3, 0, length);
            SSound.ssound_offline(engine, buf_mp3, length);
            fis.close();
        } catch (IOException e) {
            caller.onEnd(70011, "feed audio data fail");
        } finally {
        }

        stop();

    }

    public static File externalFilesDir(Context c) {
        File f = c.getExternalFilesDir(null);
        // not support android 2.1
        if (f == null || !f.exists()) {
            f = c.getFilesDir();
        }
        return f;
    }

    public void stop() {
        int r = 0;
        r = SSound.ssound_stop(engine);
        if (r != 0) caller.onEnd(70002, "engine stop error");

        r = recorder.stop();
        if (r != 0) {
            caller.onEnd(70005, "AIRecorder stop error");
        }
    }

    public void cancel() {
        int r = 0;
        r = SSound.ssound_cancel(engine);
        if (r != 0) caller.onEnd(70003, "cancel error");
    }

    public void delete() {
        int r = 0;
        r = SSound.ssound_delete(engine);
        if (r != 0) caller.onEnd(70010, "delete error");
    }

    public void playback() {

        if (recorder.running) {
            recorder.stop();
        } else {
            recorder.playback();
        }
//        recorder.playback();
    }


    public void log(String s) {
        Log.e("SingEngine", s);
    }


}
