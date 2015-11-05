package go.deyu.bloodscreen.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import go.deyu.bloodscreen.R;
import go.deyu.bloodscreen.SettingConfig;

/**
 * Created by huangeyu on 15/11/3.
 */
public class SettingFragment extends BaseFragment{

    private static final int WHAT_UPDATE_TOTAL_USE_TIME = 0x03;

    private long mTotalUseTime = 0 , mTotalUsePercent = 0;

    private Timer mUseTimer = null;

    @Bind(R.id.tv_total_use_time)TextView mTotalUseTimeTv;

    @OnClick(R.id.btn_init_total_time)
    public void initTotalTime(View v){
        cancelUpdateTodayUseTimer();
        SettingConfig.setTotalUseTime(getActivity() , 0);
        SettingConfig.setTotalUseStartTime(getActivity(),(System.currentTimeMillis()/1000));
        startUpdateTodayUseTimer();
    }


    private Handler UiHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHAT_UPDATE_TOTAL_USE_TIME :
                    mTotalUseTimeTv.setText("Total Use time : " + mTotalUseTime + "\nTotal Percent : " + mTotalUsePercent + "%");
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startUpdateTodayUseTimer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelUpdateTodayUseTimer();
    }

    private void setupPercent(){
        if(mTotalUseTime <= 0 ){
            mTotalUsePercent = 0;
            return;
        }
        long mTotalTimeStart = SettingConfig.getTotalUseStartTime(getActivity());
        long mTotalTimeFromStart = System.currentTimeMillis()/1000 - mTotalTimeStart;
        mTotalUsePercent = (mTotalUseTime*100)/mTotalTimeFromStart;
        if(mTotalUsePercent > 100) mTotalUsePercent = 100;
    }

    private void startUpdateTodayUseTimer(){
        if(mUseTimer == null) {
            mUseTimer = new Timer();
            mUseTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    mTotalUseTime = SettingConfig.getTotalUseTime(getActivity());
                    setupPercent();
                    UiHandler.sendEmptyMessage(WHAT_UPDATE_TOTAL_USE_TIME);
                }
            }, 1, 1 * 1000);
        }
    }

    private void cancelUpdateTodayUseTimer(){
        if(mUseTimer != null){
            mUseTimer.cancel();
            mUseTimer = null;
        }
    }

}
