package go.deyu.bloodscreen.app;

import android.app.Application;

import go.deyu.bloodscreen.BloodControllerInterface;
import go.deyu.bloodscreen.BloodModel;
import go.deyu.bloodscreen.BloodModelInterface;

/**
 * Created by huangeyu on 15/4/28.
 */
public class app extends Application{

    public volatile static app App;
    public BloodModelInterface model ;
    public BloodControllerInterface controller;

    public app() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App = this;
        AppContextSingleton.initialize(this);
        DeviceStatus.initialize(this);
        model = new BloodModel(this);
    }

}
