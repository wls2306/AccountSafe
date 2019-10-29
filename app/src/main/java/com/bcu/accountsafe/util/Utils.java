package com.bcu.accountsafe.util;

import android.content.Context;
import android.provider.Settings;

import java.text.SimpleDateFormat;

public class Utils {

    public static String getAndroidId (Context context) {
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        return androidId;
    }



    public static String getTimeStamp(){
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        return t;
    }

    public static String getTime(String timeStamp) {
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(timeStamp);
        String d = format.format(time);
        return d;
    }
}
