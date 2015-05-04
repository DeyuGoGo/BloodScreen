package go.deyu.bloodscreen;

import java.util.TimerTask;

/**
 * Created by huangeyu on 15/5/4.
 */
public class AddBloodTimeTask extends TimerTask {

    private BloodControllerInterface mController;

    public AddBloodTimeTask(BloodControllerInterface controller){
        this.mController = controller;
    }
    /**
     * The task to run should be specified in the implementation of the {@code run()}
     * method.
     */
    @Override
    public void run() {
        mController.addBlood();
    }
}
