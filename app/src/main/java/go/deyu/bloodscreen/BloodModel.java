package go.deyu.bloodscreen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by huangeyu on 15/4/28.
 */
public class BloodModel implements BloodModelInterface{
    private int BloodCount ;
    private final SharedPreferences prefs;
    private final String BLOOD_PERFERANCE_NAME = "Blood_Perferance";
    private final String BLOOD_COUNT_KEY = "Blood_Count";
    public BloodModel(Context context){
        prefs = context.getSharedPreferences(BLOOD_PERFERANCE_NAME, Context.MODE_PRIVATE);
        BloodCount = getBloodCount();
    }



    @Override
    public void setBlood(int blood) {
        this.BloodCount = blood;
        save();
    }

    @Override
    public int getBlood() {
        return BloodCount;
    }

    private void save(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(BLOOD_COUNT_KEY , BloodCount);
        editor.commit();
    };

    private int getBloodCount(){
        return prefs.getInt(BLOOD_COUNT_KEY , 0);
    };
}
