package com.jiuzhou.guanwang.jzcp.utils;

import android.text.TextUtils;
import android.util.Base64;


public class Base64Util {

    public static String makeUidToBase64(String strUid){
        String enUid = new String(Base64.encode(strUid.getBytes(), Base64.DEFAULT));
        return enUid;
    }
    public static String getUidFromBase64(String base64Id){
        String result ="";
        if(!TextUtils.isEmpty(base64Id)){
                result = new String(Base64.decode(base64Id.getBytes(), Base64.DEFAULT));
            }
        return result;
    }
}
