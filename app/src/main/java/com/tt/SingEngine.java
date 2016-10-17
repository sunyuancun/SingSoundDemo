package com.tt;

import android.content.Context;
import android.util.Log;

import com.singsound.singsounddemo.utils.SPUtils;
import com.xs.AIRecorder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class SingEngine {


    /**
     * 回调接口
     */
    public interface ResultListener {
        /**
         * 录音开始时回调
         */
        void onBegin();

        /**
         * 测评结果返回
         *
         * @param result 测评结果json对象
         */
        void onResult(JSONObject result);

        /**
         * 结束时回调
         *
         * @param Code 错误代码，等于0时为正常结束
         * @param msg  错误消息
         */
        void onEnd(int Code, String msg);

        /**
         * 麦克风音量回调
         *
         * @param volume 音量
         */
        void onUpdateVolume(final int volume);

        /**
         * vad 超时回调
         */
        void onVadTimeOut();

        /**
         * 录音数据回调
         *
         * @param data 录音数据
         */
        void onRecordingBuffer(byte[] data);

        /**
         * 引擎初始化成功
         */
        void onReady();
    }

    public enum coreProvideType {
        CLOUD("cloud"), NATIVE("native"), AUTO("auto");
        private String value;

        coreProvideType(String value) {
            this.setValue(value);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum coreType {
        enWord("en.word.score"),
        enSent("en.sent.score");

        private String value;

        coreType(String value) {
            this.setValue(value);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    private Context ct;
    private long engine = 0;
    private AIRecorder recorder = null;
    private ResultListener caller;
    private JSONObject newCfg = null;
    private JSONObject startCfg = null;
    private String local = null;
    private String avdLocalPath = null;
    private coreProvideType cpt = coreProvideType.CLOUD;
    private boolean useVad = false;
    private boolean needCheckInitResource = false;

    private String wavPath = "";

    public static SingEngine fragment;

    public SingEngine(Context context) {
        ct = context;
    }

    public static synchronized SingEngine newInstance(Context context) {
        if (fragment == null) {
            fragment = new SingEngine(context);
        }
        return fragment;
    }

    public String getVersion() {
        return "0.1.1";
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

    public void setOpenVad(boolean b, String vadResourcename) {
        this.useVad = b;
        if (b) NativeResource.vadResourceName = vadResourcename;
    }

    public void setOpenCheckResource(boolean checkResource) {
        this.needCheckInitResource = checkResource;
    }

    public void setListener(ResultListener listenner) {
        caller = listenner;
    }

    public String getWavPath() {
        return wavPath;
    }

    public void wavWithOutHeader(Boolean isheader) {
        recorder.setFileheader(isheader);
    }

    //=================================================================================================

    public void setNewCfg(JSONObject cfg) {
        newCfg = cfg;
    }

    public JSONObject buildInitJson(String appKey, String secretKey) throws JSONException {
        JSONObject cfg = new JSONObject();
        JSONObject cloud = new JSONObject();
        String serverAPI = "ws://api.cloud.ssapi.cn:8080";
//        String testAPI = "ws://trial.cloud.ssapi.cn:8090";
        cloud.put("enable", 1)
                .put("server", SPUtils.get(ct,SPUtils.SERVER_API,serverAPI))
                .put("connectTimeout", 20)
                .put("serverTimeout", 60);

        cfg.put("appKey", appKey != null ? appKey : "1459219202000001")
                .put("secretKey", secretKey != null ? secretKey : "3bc23f814868ebd5b61a71acde532abb")
                .put("cloud", cloud);

        return cfg;
    }

    public void setStartCfg(JSONObject cfg) throws JSONException {
        startCfg = cfg;
    }

    public JSONObject buildStartJson(JSONObject request) throws JSONException {
        JSONObject cfg = new JSONObject();
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
        buildNativeInitJson();
        buildAvdInitJson();
        log("NewCfg" + newCfg.toString());

        engine = SSound.ssound_new(newCfg.toString(), ct);
        if (engine == 0) caller.onEnd(60001, "please check param");

        recorder = AIRecorder.getInstance();
        caller.onReady();
    }

    public int SSoundStart(byte[] rid) {
        checkServerTypeWhenAutoType();
        buildAvdStartJson();

        log("StartCfg" + startCfg.toString());

        int r = SSound.ssound_start(engine, startCfg.toString(), rid, new SSound.ssound_callback() {
            @Override
            public int run(byte[] id, int type, byte[] data, int size) {
                if (type == SSound.SSOUND_MESSAGE_TYPE_JSON) {
                    final String result = new String(data, 0, size).trim();
                    try {
                        final JSONObject json = new JSONObject(result);
                        if (json.has("vad_status") || json.has("sound_intensity")) {
                            if (json.optInt("vad_status") == 2 && recorder.running) {
                                caller.onVadTimeOut();    //监听到没有录音  自动stop
                                stop();
                            }
                        } else {
                            caller.onEnd(0, "");
                            caller.onResult(json);         //返回测评结果json
                        }
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
                    for (byte element : data) {
                        v += element * element;
                    }
                    double mean = v / (double) size;
                    double volume = 100 * Math.log10(mean);
                    caller.onUpdateVolume((int) volume);
                    caller.onRecordingBuffer(data);
                    int rr = SSound.ssound_feed(engine, data, size);
                    if (rr != 0) {
                        stop();
                    }
                }
            }
        });
        if (r != 0) {
            caller.onEnd(70004, "AIRecorder start error");
        }

        caller.onBegin();
    }

    public void stop() {
        int r;

        r = recorder.stop();
        if (r != 0) {
            caller.onEnd(70005, "AIRecorder stop error");
        }

        r = SSound.ssound_stop(engine);
        if (r != 0) {
            caller.onEnd(70002, "engine stop error");
        }


    }

    public void cancel() {
        int r;

        r = recorder.stop();
        if (r != 0) {
            caller.onEnd(70005, "AIRecorder stop error");
        }

        r = SSound.ssound_cancel(engine);
        if (r != 0) caller.onEnd(70003, "cancel error");
    }

    public void delete() {
        int r;
        r = SSound.ssound_delete(engine);
        if (r != 0) caller.onEnd(70010, "delete error");
    }

    public void playback() {

        if (recorder.running) {
            recorder.stop();
        } else {
            recorder.playback();
        }
    }


    private String buildAvdPath() throws JSONException {

        if (needCheckInitResource) {
            avdLocalPath = AiUtil.getFilePathFromAssets(ct, NativeResource.vadResourceName);
        } else {
            if (avdLocalPath == null) {
                avdLocalPath = AiUtil.getFilePathFromAssets(ct, NativeResource.vadResourceName);
            }
        }

        return avdLocalPath;
    }

    private JSONObject buildNativePath() throws JSONException {

        if (needCheckInitResource) {
            local = AiUtil.unzipFile(ct, NativeResource.zipResourceName).toString();
        } else {
            if (local == null) {
                local = AiUtil.unzipFile(ct, NativeResource.zipResourceName).toString();
            }
        }

        String res_path = String.format(NativeResource.native_zip_res_path, local, local);
        return new JSONObject(res_path);
    }

    private void buildNativeInitJson() throws JSONException {
        if (cpt != coreProvideType.CLOUD && !newCfg.has("native")) {
            newCfg.put("native", buildNativePath());
        }
    }

    private void buildAvdInitJson() {
        try {
            if (useVad) {
                JSONObject vad = new JSONObject();
                vad.put("enable", 1).put("res", buildAvdPath());
                newCfg.put("vad", vad);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void buildAvdStartJson() {
        try {
            if (useVad) {
                // vad仅支持单词句子
                String coreTypeString = startCfg.optJSONObject("request").optString("coreType");
                if (coreTypeString != null && (coreTypeString.equals(coreType.enWord.getValue())
                        || coreTypeString.equals(coreType.enSent.getValue()))) {
                    JSONObject vad = new JSONObject();
                    vad.put("vadEnable", 1);
                    startCfg.put("vad", vad);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置Auto模式时动态切换引擎Type ， 有网cloud ，没网native
     */
    private void checkServerTypeWhenAutoType() {
        try {
            if (cpt != coreProvideType.AUTO) {
                return;
            }

            if (!NetWorkUtil.getInstance().isConnected(ct)) {
                startCfg.put("coreProvideType", coreProvideType.NATIVE.getValue());
            } else {
                startCfg.put("coreProvideType", coreProvideType.CLOUD.getValue());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void log(String s) {
        Log.d("SingEngine", s);
    }

    /**
     * 离线WAV 文件评测
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

}
