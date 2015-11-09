package go.deyu.dailyphoneuse;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by huangeyu on 15/10/15.
 */
public class SettingConfig {
    private final static String PERFERANCE_SETTING = "LovePhone_Setting_Perferance";
    private final static String KEY_SETTING_LOVE_OPEN = "love.open";
    private final static String KEY_SETTING_COUNTING_OPEN = "counting.open";
    private final static String KEY_SETTING_TOTAL_USE_TIME = "use.time";
    private final static String KEY_SETTING_TOTAL_START_USE_TIME = "use.start.time";
    private final static String KEY_SETTING_IMAGE_TYPE = "image.type";

    public static void setIsLoveOpen(Context context,boolean isOpen) {
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE  );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_SETTING_LOVE_OPEN , isOpen);
        editor.commit();
    }

    public static boolean getIsLoveOpen(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        return prefs.getBoolean(KEY_SETTING_LOVE_OPEN,false);
    }

    public static void setIsCountingOpen(Context context,boolean isOpen) {
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_SETTING_COUNTING_OPEN , isOpen);
        editor.commit();
    }

    public static boolean getIsCountingOpen(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        return prefs.getBoolean(KEY_SETTING_COUNTING_OPEN , false);
    }

    public static void setTotalUseTime(Context context,long useTime) {
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_SETTING_TOTAL_USE_TIME, useTime);
        editor.commit();
    }

    public static long getTotalUseTime(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        return prefs.getLong(KEY_SETTING_TOTAL_USE_TIME, 0);
    }

    public static void setTotalUseStartTime(Context context,long useTime) {
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_SETTING_TOTAL_START_USE_TIME, useTime);
        editor.commit();
    }

    public static long getTotalUseStartTime(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        return prefs.getLong(KEY_SETTING_TOTAL_START_USE_TIME, 0);
    }
    public static void setImageType(Context context,int type) {
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_SETTING_IMAGE_TYPE, type);
        editor.commit();
    }

    public static int getImageType(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PERFERANCE_SETTING , Context.MODE_PRIVATE );
        return prefs.getInt(KEY_SETTING_IMAGE_TYPE, 0);
    }
}
