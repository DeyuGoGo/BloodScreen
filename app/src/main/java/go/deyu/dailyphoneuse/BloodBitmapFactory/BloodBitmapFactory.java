package go.deyu.dailyphoneuse.BloodBitmapFactory;

import android.graphics.Bitmap;

/**
 * Created by huangeyu on 15/5/5.
 */
public interface BloodBitmapFactory {
    public static int TYPE_BLOOD = 0x00;
    public static int TYPE_CANDY = 0x01;
    public static int TYPE_CIRCLE = 0x02;
    public Bitmap createBloodBitmap(int w , int h);
}