package go.deyu.bloodscreen;

import android.content.Context;

import java.util.TimerTask;


/**
 * Created by huangeyu on 15/5/4.
 */
public class AddUseTimeTask extends TimerTask {

    private BloodControllerInterface mController;

    public AddUseTimeTask(BloodControllerInterface controller){
        this.mController = controller;
    }
    /**
     * The task to run should be specified in the implementation of the {@code run()}
     * method.
     */
    @Override
    public void run() {
        mController.addUseSecond();
        long TotalUseTime = SettingConfig.getTotalUseTime((Context)mController);
        SettingConfig.setTotalUseTime((Context)mController, ++TotalUseTime);
    }
}
