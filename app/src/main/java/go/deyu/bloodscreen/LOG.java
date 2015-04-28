package go.deyu.bloodscreen;

import android.util.Log;

public class LOG {
	
    public static boolean DEBUG = true;
    public static final String LOGTAG = "[BloodScreen]";

    
    public static void v(String TAG, String Info) {
        if (DEBUG) Log.v(LOGTAG + TAG,  Info);
    }

    public static void v(String TAG, String Info, Throwable tr) {
        if (DEBUG) Log.v(LOGTAG + TAG,  Info, tr);
    }

    
    public static void w(String TAG, String Info) {
        if (DEBUG) Log.w(LOGTAG + TAG,   Info);
    }

    public static void w(String TAG, String Info, Throwable tr) {
        if (DEBUG) Log.w(LOGTAG + TAG,   Info, tr);
    }

    public static void w(String TAG, Throwable tr) {
        if (DEBUG) Log.w(LOGTAG + TAG, tr);
    }

    
    public static void i(String TAG, String Info) {
        if (DEBUG) Log.i(LOGTAG + TAG,   Info);
    }

    public static void i(String TAG, String Info, Throwable tr) {
        if (DEBUG) Log.i(LOGTAG + TAG,  Info, tr);
    }

    
    public static void d(String TAG, String Info) {
        if (DEBUG) Log.d(LOGTAG + TAG,   Info);
    }

    public static void d(String TAG, String Info, Throwable tr) {
        if (DEBUG) Log.d(LOGTAG + TAG,   Info, tr);
    }

    
    public static void e(String TAG, String Info) {
        Log.e(LOGTAG + TAG,   Info);
    }

    public static void e(String TAG, String Info, Throwable tr) {
        Log.e(LOGTAG + TAG,   Info, tr);
    }
    
    
}
