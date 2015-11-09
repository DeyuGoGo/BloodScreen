package go.deyu.dailyphoneuse;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by huangeyu on 15/4/28.
 */
public class BloodModel implements BloodModelInterface{
    private long BloodCount = 0;
    private long UseTime = 0;
    private long needAdd = 0;
    private boolean isCountChange = true;
    private int howManySecondOneBlood = 5;
    private final SharedPreferences prefs;
    private final String BLOOD_PERFERANCE_NAME = "Blood_Perferance";
    private final String KEY_USE_TIME_COUNT = "use.time.count";
    private OnCountChangeListener mListener;

    public BloodModel(Context context ){
        prefs = context.getSharedPreferences(BLOOD_PERFERANCE_NAME, Context.MODE_PRIVATE);
        UseTime = getUseTimeSp();
        needAdd = UseTime;
    }

    public void setmListener(OnCountChangeListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public long getBlood() {
        BloodCount = UseTime / howManySecondOneBlood;
        return BloodCount;
    }


    @Override
    public long getUseTime() {
        return UseTime;
    }

    @Override
    public void setUseTime(long time) {
        this.UseTime = time;
        save();
    }

    @Override
    public void incrementUseTime() {
        this.UseTime++;
        needAdd++;
        setupNextTime();
        if(isCountChange)OnCountChange();
        save();
    }


    private void setupNextTime(){
        if(needAdd<=howManySecondOneBlood)return;
        needAdd -= howManySecondOneBlood;
        BloodCount++;
        isCountChange = true;
        if(howManySecondOneBlood<60)howManySecondOneBlood++;
        setupNextTime();
    }

    private void save(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_USE_TIME_COUNT, UseTime);
        editor.commit();
    };

    private void OnCountChange(){
        if(mListener!=null)
         mListener.OnCountChange();
        isCountChange=false;
    }

    private long getUseTimeSp(){
        return prefs.getLong(KEY_USE_TIME_COUNT, 0);
    };

    public interface OnCountChangeListener{
        public void OnCountChange();
    }
}
