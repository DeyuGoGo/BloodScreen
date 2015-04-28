package go.deyu.bloodscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import go.deyu.bloodscreen.app.app;

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
        app.App.controller.addBlood();
    }
}
