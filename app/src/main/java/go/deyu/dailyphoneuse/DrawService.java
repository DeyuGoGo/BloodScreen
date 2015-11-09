package go.deyu.dailyphoneuse;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.WindowManager;

import java.util.Timer;

import go.deyu.dailyphoneuse.alarm.ChangeDayAlarm;
import go.deyu.dailyphoneuse.app.app;
import go.deyu.util.DailyCheck;
import go.deyu.util.LOG;

public class DrawService extends Service implements BloodControllerInterface{
    private final String TAG = getClass().getSimpleName();

    public static final String ACTION_START_ADD_TIMER = "action.start.add.timer" ;

    public static final String ACTION_CANCEL_ADD_TIMER = "action.cancel.add.timer" ;

    public static final String ACTION_CLEAN = "action.clean" ;

    public static final String ACTION_SHOW = "action.show" ;

    public static final String ACTION_NOT_SHOW = "action.not.show" ;

    public static final String ACTION_CHECK_CHANGE_DAY = "action.check.change.day" ;

    public static final String ACTION_REFRESH_VIEW = "action.refresh.view" ;

    private BloodView mBloodView = null;
    private BloodModelInterface model ;
    private PhoneUseReceiver mPhoneUseReceiver;
    private final int mAddBloodTime = 1;//second
    private Timer mAddUseTimer = null;
    private WindowManager wm;
    private boolean mIsShowView = false;

    private BloodModel.OnCountChangeListener listener = new BloodModel.OnCountChangeListener() {
        @Override
        public void OnCountChange() {
            if(mIsShowView)
                mBloodView.postInvalidate();
        }
    };

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
        model.setmListener(listener);
        initReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent == null || intent.getAction() ==null){
            checkDayChange();
            if(SettingConfig.getIsCountingOpen(this))
                startAddBloodTimer();
            if(SettingConfig.getIsLoveOpen(this))
                showBloodView();
            return START_STICKY;
        }

        if(intent.getAction().equals(ACTION_START_ADD_TIMER)){
            setupStartTime();
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
        if(intent.getAction().equals(ACTION_CHECK_CHANGE_DAY)){
            checkDayChange();
        }
        if(intent.getAction().equals(ACTION_REFRESH_VIEW)){
            if(SettingConfig.getIsLoveOpen(this)){
                dismissBloodView();
                showBloodView();
            }
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
        model.setmListener(null);
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
    public void addUseSecond() {
        LOG.d(TAG, "addBlood");
        model.incrementUseTime();
    }

    @Override
    public void cleanBlood() {
        model.setUseTime(0);
        if(mIsShowView){
            mBloodView.clean();
            mBloodView.postInvalidate();
        }

    }

    private void setupStartTime(){
        if(SettingConfig.getTotalUseStartTime(this) == 0){
            SettingConfig.setTotalUseStartTime(this, (System.currentTimeMillis()/1000));
        }
    }

    private void checkDayChange(){
        if(DailyCheck.isChangeDay())
            resetDailyCount();
    }

    private void resetDailyCount(){
        Intent i = new Intent(this,DrawService.class);
        i.setAction(ACTION_CHECK_CHANGE_DAY);
        ChangeDayAlarm.setChangeDayAlarmStartService(this,i);
        cleanBlood();
        DailyCheck.updateDayTime();
    }

    private void startAddBloodTimer(){
        if(mAddUseTimer == null) {
            mAddUseTimer = new Timer();
            mAddUseTimer.scheduleAtFixedRate(new AddUseTimeTask(this), 1, mAddBloodTime * 1000);
        }
    }

    private void cancelAddBloodTimer(){
        if(mAddUseTimer != null){
            mAddUseTimer.cancel();
            mAddUseTimer = null;
        }
    }

}
