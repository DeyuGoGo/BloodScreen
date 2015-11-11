package go.deyu.dailyphoneuse.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import go.deyu.util.LOG;

/**
 * Created by huangeyu on 15/11/4.
 */
public class ChangeDayAlarm {

    private static  final String TAG = "ChangeDayAlarm";
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void setChangeDayAlarmStartService(Context context , Intent i){

        boolean alarmUp = (PendingIntent.getService(context, 0 ,
                i ,
                PendingIntent.FLAG_NO_CREATE) != null);
        if(alarmUp)return;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE , 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND , 0);

        PendingIntent pi = PendingIntent.getService(context, 0, i , PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        LOG.d(TAG , "set Time : " + sdf.format(cal.getTime()));

    }

}
