package com.tt;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class nativesource {
	
	 private String native_res_path2 = "{"
	            + "\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.splp.0.11\"}"
	            + ",\"en.word.score\":{\"resDirPath\": \"%s/eval/bin/eng.wrd.splp.1.7\"}"
//	            + ",\"cn.word.score\":{\"resDirPath\": \"%s/eval/bin/chn.wrd.gnr.splp.offline.0.4\"}"
//	            + ",\"cn.sent.score\":{\"resDirPath\": \"%s/eval/bin/chn.snt.splp.offline.0.2\"} "
//	            + "\"en.pred.exam\":{\"resDirPath\": \"%s/exam/bin/eng.pred.0.0.8\"}"
	            + "}";
	
	public JSONObject initnav(JSONObject cfg, Context c){
		 String res_path = new String();
         String resourceDir = new String(AiUtil.unzipFile(c,
                 "resources.zip").toString());
         res_path = String.format(native_res_path2, resourceDir, resourceDir
                 , resourceDir, resourceDir, resourceDir);
         try {
			cfg.put("native", new JSONObject(res_path));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         return cfg;
	}
}
