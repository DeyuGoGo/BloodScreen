package go.deyu.bloodscreen;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.WindowManager;

import java.util.Timer;

import go.deyu.bloodscreen.app.app;
import go.deyu.util.LOG;

public class DrawService extends Service implements BloodControllerInterface{
    private final String TAG = getClass().getSimpleName();

    public static final String ACTION_START_ADD_TIMER = "action.start.add.timer" ;

    public static final String ACTION_CANCEL_ADD_TIMER = "action.cancel.add.timer" ;

    public static final String ACTION_CLEAN = "action.clean" ;

    public static final String ACTION_SHOW = "action.show" ;

    public static final String ACTION_NOT_SHOW = "action.not.show" ;



    private BloodView mBloodView = null;
    private BloodModelInterface model ;
    private PhoneUseReceiver mPhoneUseReceiver;
    private final int mAddBloodTime = 1;//second
    private Timer mAddBloodTimer = null;
    private WindowManager wm;
    private boolean mIsShowView = false;

    public DrawService() {
    }
    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        this.model = app.App.model;
        initReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent == null || intent.getAction() ==null)return START_STICKY;

        if(intent.getAction().equals(ACTION_START_ADD_TIMER)){
            startAddBloodTimer();
        }
        if(intent.getAction().equals(ACTION_CANCEL_ADD_TIMER)){
            cancelAddBloodTimer();
        }
        if(intent.getAction().equals(ACTION_CLEAN)){
            cleanBlood();
        }
        if(intent.getAction().equals(ACTION_SHOW)){
            showBloodView();
        }
        if(intent.getAction().equals(ACTION_NOT_SHOW)){
            dismissBloodView();
        }
        return START_STICKY;
    }

    private void initReceiver(){
        mPhoneUseReceiver = new PhoneUseReceiver();
        registerReceiver(mPhoneUseReceiver, mPhoneUseReceiver.getIntentFilter());
    }

    private void showBloodView(){
        if(mBloodView==null) {
            mBloodView = new RandomSizeBloodView(this);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
            params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            params.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            params.format = PixelFormat.TRANSPARENT;
            wm.addView(mBloodView, params);
            mIsShowView = true;
        }
    }
    private void dismissBloodView(){
        if(mBloodView!=null){
            wm.removeView(mBloodView);
            mBloodView = null;
            mIsShowView = false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(mIsShowView){
            dismissBloodView();
            showBloodView();
        }
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
        if(mPhoneUseReceiver!=null)
            unregisterReceiver(mPhoneUseReceiver);
        if(mIsShowView)
            dismissBloodView();
        cancelAddBloodTimer();
    }


//  this server not use bind function
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void addBlood() {
        LOG.d(TAG, "addBlood");
        int i = model.getBlood();
        i++;
        model.setBlood(i);
        if(mIsShowView)
            mBloodView.postInvalidate();
    }

    @Override
    public void cleanBlood() {
        model.setBlood(0);
        if(mIsShowView){
            mBloodView.clean();
            mBloodView.postInvalidate();
        }

    }

    private void startAddBloodTimer(){
        if(mAddBloodTimer == null) {
            mAddBloodTimer = new Timer();
            mAddBloodTimer.scheduleAtFixedRate(new AddBloodTimeTask(this), 1, mAddBloodTime * 1000);
        }
    }

    private void cancelAddBloodTimer(){
        if(mAddBloodTimer != null){
            mAddBloodTimer.cancel();
            mAddBloodTimer = null;
        }
    }
}
