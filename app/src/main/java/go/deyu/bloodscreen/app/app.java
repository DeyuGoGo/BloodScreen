package go.deyu.bloodscreen.app;

import android.app.Application;

import go.deyu.bloodscreen.BloodModel;
import go.deyu.bloodscreen.BloodModelInterface;
import go.deyu.util.AppContextSingleton;
import go.deyu.util.DeviceStatus;
import go.deyu.util.LOG;

/**
 * Created by huangeyu on 15/4/28.
 */
public class app extends Application{

    public volatile static app App;
    public static BloodModelInterface model ;

    public app() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App = this;
        AppContextSingleton.initialize(this);
        DeviceStatus.initialize(this);
        LOG.LOGTAG = getString(getApplicationInfo().labelRes);
        model = new BloodModel(this);
    }

}
