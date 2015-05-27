package go.deyu.bloodscreen.BloodBitmapFactory;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import go.deyu.bloodscreen.R;

/**
 * Created by huangeyu on 15/5/5.
 */
public class ResourceBloodBitmapFactory implements BloodBitmapFactory{
    private Bitmap[] bitmaps;
    private Context mContext;
    private Random mRandom;

    public ResourceBloodBitmapFactory(Context context){
        mContext = context;
        mRandom = new Random();
        init();
    }

    private void init(){
        Resources res = mContext.getResources();
        bitmaps = new Bitmap[4];
        bitmaps[0] = BitmapFactory.decodeResource(res, R.drawable.blood1);
        bitmaps[1] = BitmapFactory.decodeResource(res, R.drawable.blood2);
        bitmaps[2] = BitmapFactory.decodeResource(res, R.drawable.blood3);
        bitmaps[3] = BitmapFactory.decodeResource(res, R.drawable.blood4);
    }

    @Override
    public Bitmap createBloodBitmap(int w, int h) {
        int randomBloodindex  = mRandom.nextInt(4);
        return Bitmap.createScaledBitmap( bitmaps[randomBloodindex], w , h ,true);
    }
}
