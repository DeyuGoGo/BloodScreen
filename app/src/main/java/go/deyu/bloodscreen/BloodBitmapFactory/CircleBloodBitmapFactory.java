package go.deyu.bloodscreen.BloodBitmapFactory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by huangeyu on 15/5/5.
 */
public class CircleBloodBitmapFactory implements BloodBitmapFactory{
    private Paint p;

    public CircleBloodBitmapFactory(){
        initPaint();
    }

    private void initPaint(){
        p = new Paint();
        p.setColor(Color.RED);
    }

    @Override
    public Bitmap createBloodBitmap(int w, int h) {
        Bitmap b = Bitmap.createBitmap(w , h , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        float radius = w > h ? h / 2 : w / 2 ;
        canvas.drawCircle(radius , radius , radius , p);
        return b;
    }
}
