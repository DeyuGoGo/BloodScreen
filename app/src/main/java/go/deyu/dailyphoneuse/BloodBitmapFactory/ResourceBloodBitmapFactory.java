package go.deyu.dailyphoneuse.BloodBitmapFactory;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import go.deyu.dailyphoneuse.R;

/**
 * Created by huangeyu on 15/5/5.
 */
public class ResourceBloodBitmapFactory implements BloodBitmapFactory{
    private Bitmap[] bitmaps;
    private Context mContext;
    private Random mRandom;
    public ResourceBloodBitmapFactory(Context context , ImageType type){
        mContext = context;
        mRandom = new Random();
        init(type);
    }

    private void init(ImageType type){
        Resources res = mContext.getResources();
        switch (type){
            case BLOOD:
                bitmaps = new Bitmap[4];
                bitmaps[0] = BitmapFactory.decodeResource(res, R.drawable.blood1);
                bitmaps[1] = BitmapFactory.decodeResource(res, R.drawable.blood2);
                bitmaps[2] = BitmapFactory.decodeResource(res, R.drawable.blood3);
                bitmaps[3] = BitmapFactory.decodeResource(res, R.drawable.blood4);
                break;
            case CANDY:
                bitmaps = new Bitmap[4];
                bitmaps[0] = BitmapFactory.decodeResource(res, R.drawable.candy1);
                bitmaps[1] = BitmapFactory.decodeResource(res, R.drawable.candy2);
                bitmaps[2] = BitmapFactory.decodeResource(res, R.drawable.candy3);
                bitmaps[3] = BitmapFactory.decodeResource(res, R.drawable.candy4);
                break;
        }

    }



    @Override
    public Bitmap createBloodBitmap(int w, int h) {
        int randomBloodindex  = mRandom.nextInt(bitmaps.length);
        return Bitmap.createScaledBitmap( bitmaps[randomBloodindex], w , h ,true);
    }
}
