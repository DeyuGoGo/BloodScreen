package go.deyu.dailyphoneuse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import go.deyu.util.LOG;

public class PhoneUseReceiver extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();


    public PhoneUseReceiver() {
    }


    public IntentFilter getIntentFilter(){
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        return intentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context , DrawService.class);
        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            LOG.d(TAG , "ACTION_SCREEN_ON");
            i.setAction(DrawService.ACTION_START_ADD_TIMER);
            context.startService(i);
        }
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            LOG.d(TAG , "ACTION_SCREEN_OFF");
            i.setAction(DrawService.ACTION_CANCEL_ADD_TIMER);
            context.startService(i);
        }
    }
}
