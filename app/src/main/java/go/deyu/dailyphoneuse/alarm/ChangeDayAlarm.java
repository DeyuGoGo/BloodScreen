package go.deyu.dailyphoneuse.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by huangeyu on 15/11/4.
 */
public class ChangeDayAlarm {

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
    }
}
