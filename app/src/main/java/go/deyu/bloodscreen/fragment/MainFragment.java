package go.deyu.bloodscreen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import go.deyu.bloodscreen.DrawService;
import go.deyu.bloodscreen.R;
import go.deyu.bloodscreen.SettingConfig;
import go.deyu.bloodscreen.app.app;

/**
 * Created by huangeyu on 15/11/3.
 */
public class MainFragment extends BaseFragment{

    private Timer mUseTimer = null;
    
    @Bind(R.id.switch_love_open)Switch mSwitchLove;

    @Bind(R.id.switch_count_phone_open)Switch mSwitchCount;

    @Bind(R.id.textView)TextView mUseTimeTv;

    @OnClick(R.id.main_btn_clean)
    public void clean(){
        Intent i = new Intent(getActivity(),DrawService.class);
        i.setAction(DrawService.ACTION_CLEAN);
        getActivity().startService(i);
    }

    private static final int WHAT_UPDATE_TODAY_USE_TIME = 0x02;

    private Handler UiHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHAT_UPDATE_TODAY_USE_TIME:
                    mUseTimeTv.setText("Use time : " + app.App.model.getUseTime());
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwitchLove.setChecked(SettingConfig.getIsLoveOpen(getActivity()));
        mSwitchLove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingConfig.setIsLoveOpen(getActivity(), isChecked);
                setLoveVisible(isChecked);
            }
        });
        mSwitchCount.setChecked(SettingConfig.getIsCountingOpen(getActivity()));
        mSwitchCount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingConfig.setIsCountingOpen(getActivity(), isChecked);
                setCountingStart(isChecked);
            }
        });
        startUpdateTodayUseTimer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelUpdateTodayUseTimer();
    }

    private void startUpdateTodayUseTimer(){
        if(mUseTimer == null) {
            mUseTimer = new Timer();
            mUseTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    UiHandler.sendEmptyMessage(WHAT_UPDATE_TODAY_USE_TIME);
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

    private void setLoveVisible(boolean visible){
        Intent i = new Intent(getActivity(),DrawService.class);
        if(visible)
            i.setAction(DrawService.ACTION_SHOW);
        else
            i.setAction(DrawService.ACTION_NOT_SHOW);
        getActivity().startService(i);
    }

    private void setCountingStart(boolean start){
        Intent i = new Intent(getActivity(),DrawService.class);
        if(start)
            i.setAction(DrawService.ACTION_START_ADD_TIMER);
        else
            i.setAction(DrawService.ACTION_CANCEL_ADD_TIMER);
        getActivity().startService(i);
    }

}
