package com.tt;

public class NativeResource {

    public static String vadResourceName = "vad.0.1.bin";
    public static String zipResourceName = "resource.zip";


    public static String native_zip_res_path = "{"
            + "\"en.sent.score\":{\"res\": \"%s/eval/bin/eng.snt.pydnn.16bit\"}"
            + ",\"en.word.score\":{\"res\": \"%s/eval/bin/eng.wrd.pydnn.16bit\"}"
            + "}";

    public static String[] native_zip_file_names =  {"comb.db","common.bin",
            "eng.snt.pydnn.16bit.bin","eng.snt.pydnn.16bit.cfg",
            "eng.wrd.pydnn.16bit.bin","eng.wrd.pydnn.16bit.cfg"};

}
