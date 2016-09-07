/*******************************************************************************
 * Copyright
 ******************************************************************************/
package com.tt;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * JSON put/get utils
 */
public class JSONUtil {

    /**
     * Put key/value to JSONObject with no JSONException throws
     * 
     * @param json
     *            JSONObject
     * @param key
     *            key
     * @param value
     *            value
     */
    public static void putQuietly(JSONObject json, String key, Object value) {
        try {
            json.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Put Map entries to JSONObject with no JSONException throws
     * 
     * @param json
     *            JSONObject
     * @param map
     *            key/value map
     */
    public static void putQuietly(JSONObject json, Map<String, Object> map) {
        if (map == null)
            return;
        for (Map.Entry<String, Object> e : map.entrySet()) {
            putQuietly(json, e.getKey(), e.getValue());
        }
    }

    /**
     * Get value from JSONObject
     * 
     * @param json
     *            JSONObject
     * @param key
     *            key
     * @return value of key
     */
    public static final Object getQuietly(JSONObject json, String key) {
        try {
            return json.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get String value from JSONObject
     * 
     * @param json
     *            JSONObject
     * @param key
     *            key
     * @return value of key
     */
    public static final String getStringQuietly(JSONObject json, String key) {
        try {
            return json.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return a new JSONObject and put Bundle key/values
     * 
     * @param bundle
     *            bundle
     * @return JSONObject
     */
    public static JSONObject bundleToJSON(Bundle bundle) {
        JSONObject json = new JSONObject();
        for (Iterator<String> it = bundle.keySet().iterator(); it.hasNext();) {
            String key = it.next();
            putQuietly(json, key, bundle.get(key));
        }
        return json;
    }

    /**
     * Return a new JSONObject and put a pair key/value
     * 
     * @param key
     * @param value
     * @return JSONObject
     */
    public static JSONObject pairToJSON(String key, Object value) {
        JSONObject json = new JSONObject();
        putQuietly(json, key, value);
        return json;
    }

    /**
     * Return a new JSONObject and put map key/values
     * 
     * @param map
     * @return JSONObject
     */
    public static JSONObject mapToJSON(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        putQuietly(json, map);
        return json;
    }

    /**
     * 默认每次缩进两个空格
     */
    private static final String empty="  ";

    public static String format(String json){
        try {
            int empty=0;
            char[]chs=json.toCharArray();
            StringBuilder stringBuilder=new StringBuilder();
            for (int i = 0; i < chs.length;) {
                //若是双引号，则为字符串，下面if语句会处理该字符串
                if (chs[i]=='\"') {

                    stringBuilder.append(chs[i]);
                    i++;
                    //查找字符串结束位置
                    for ( ; i < chs.length;) {
                        //如果当前字符是双引号，且前面有连续的偶数个\，说明字符串结束
                        if ( chs[i]=='\"'&&isDoubleSerialBackslash(chs,i-1)) {
                            stringBuilder.append(chs[i]);
                            i++;
                            break;
                        } else{
                            stringBuilder.append(chs[i]);
                            i++;
                        }

                    }
                }else if (chs[i]==',') {
                    stringBuilder.append(',').append('\n').append(getEmpty(empty));

                    i++;
                }else if (chs[i]=='{'||chs[i]=='[') {
                    empty++;
                    stringBuilder.append(chs[i]).append('\n').append(getEmpty(empty));

                    i++;
                }else if (chs[i]=='}'||chs[i]==']') {
                    empty--;
                    stringBuilder.append('\n').append(getEmpty(empty)).append(chs[i]);

                    i++;
                }else {
                    stringBuilder.append(chs[i]);
                    i++;
                }


            }



            return stringBuilder.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return json;
        }

    }
    private static boolean isDoubleSerialBackslash(char[] chs, int i) {
        int count=0;
        for (int j = i; j >-1; j--) {
            if (chs[j]=='\\') {
                count++;
            }else{
                return count%2==0;
            }
        }

        return count%2==0;
    }

    /**
     * 缩进
     * @param count
     * @return
     */
    private static String getEmpty(int count){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < count; i++) {
            stringBuilder.append(empty) ;
        }

        return stringBuilder.toString();
    }

}

