package go.deyu.bloodscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class PhoneUseReceiver extends BroadcastReceiver {

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
            i.setAction(DrawService.ACTION_START_ADD_TIMER);
            context.startService(i);
        }
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            i.setAction(DrawService.ACTION_CANCEL_ADD_TIMER);
            context.startService(i);
        }
    }
}
