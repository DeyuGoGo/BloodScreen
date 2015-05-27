package go.deyu.bloodscreen.app;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by huangeyu on 15/5/13.
 */
public class DeviceStatus {

    private volatile static DeviceStatus uniqueDeviceStatus;
    private Context mContext;

    private DeviceStatus(Context context){
        this.mContext = context;

    }

    public static DeviceStatus initialize(Context context){
        if(uniqueDeviceStatus!=null){
            return uniqueDeviceStatus;
        }
        synchronized (DeviceStatus.class) {
            if(uniqueDeviceStatus==null){
                uniqueDeviceStatus = new DeviceStatus(context);

            }
        }
        return uniqueDeviceStatus;
    }

    public static DeviceStatus getInstance(){
        return uniqueDeviceStatus;
    }

    public boolean isScreenOn(){
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }

}
