package go.deyu.bloodscreen;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.WindowManager;

import java.util.Timer;

import go.deyu.bloodscreen.app.app;

public class DrawService extends Service implements BloodControllerInterface{
    private final String TAG = getClass().getSimpleName();
    private BloodView mBloodView;
    private BloodModelInterface model ;
    private PhoneUseReceiver mPhoneUseReceiver;
    private final int mAddBloodTime = 30;//second
    private Timer mAddBloodTimer;
    public DrawService() {
    }
    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        app.App.controller = this;
        this.model = app.App.model;
        initReceiver();
        initBloodView();
        startAddBloodTimer();
    }

    private void initReceiver(){
        mPhoneUseReceiver = new PhoneUseReceiver();
        registerReceiver(mPhoneUseReceiver, mPhoneUseReceiver.getIntentFilter());
    }

    private void initBloodView(){
        mBloodView = new RandomSizeBloodView(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.format = PixelFormat.TRANSLUCENT;
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(mBloodView, params);
    }


    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.  The
     * service should clean up any resources it holds (threads, registered
     * receivers, etc) at this point.  Upon return, there will be no more calls
     * in to this Service object and it is effectively dead.  Do not call this method directly.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        app.App.controller = null;
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.removeView(mBloodView);
        if(mPhoneUseReceiver!=null)unregisterReceiver(mPhoneUseReceiver);
        stopAddBloodTimer();
    }



    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startAddBloodTimer(){
        mAddBloodTimer = new Timer();
        mAddBloodTimer.scheduleAtFixedRate(new AddBloodTimeTask(this), 1, mAddBloodTime * 1000);
    }
    private void stopAddBloodTimer(){
        if(mAddBloodTimer != null) mAddBloodTimer.cancel();
    }


    @Override
    public void addBlood() {
        int i = model.getBlood();
        i++;
        model.setBlood(i);
        mBloodView.postInvalidate();
    }

    @Override
    public void cleanBlood() {
        model.setBlood(0);
        mBloodView.clean();
        mBloodView.postInvalidate();
    }
}
