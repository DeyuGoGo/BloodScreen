package go.deyu.bloodscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;

import go.deyu.bloodscreen.app.app;


/**
 * TODO: document your custom view class.
 */
public class BloodView extends View {

    protected Random mRandom;

    private final String TAG = getClass().getSimpleName();

    private holder mHolder = null;
    private int m_nScreenW, m_nScreenH;
    private Paint mBloodPaint;
    private int defaultBloodRadius = 2;


    public BloodView(Context context) {
        super(context);
        init();
    }

    public BloodView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BloodView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mHolder = new holder();
        mRandom = new Random();
        mBloodPaint = new Paint();
        mBloodPaint.setColor(Color.RED);
        initDrawConfig();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawBloods(mHolder.canvas);
        Bitmap bitmap = mHolder.bitmap;
        canvas.drawBitmap(bitmap,
                new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), null);
    }

    private void initDrawConfig() {
        WindowManager winMgr = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        winMgr.getDefaultDisplay().getSize(size);
        m_nScreenW = size.x;
        m_nScreenH = size.y;
        LOG.d(TAG, "m_nScreenW: " + m_nScreenW + " m_nScreenH : " + m_nScreenH);
    }

    private void drawBloods(Canvas canvas) {
        int len = app.App.model.getBlood();
        for (int i = mHolder.blood_number; i < len; i++) {
            drawBlood(canvas);
        }
        mHolder.blood_number = len;
    }

    private void drawBlood(Canvas canvas) {
        int size = getBloodRadius();
        int x = mRandom.nextInt((m_nScreenW));
        int y = mRandom.nextInt((m_nScreenH));
        canvas.drawCircle(x, y, size, mBloodPaint);
    }

    protected int getBloodRadius() {
        return defaultBloodRadius;
    }

    // no use
    class holder {
        public int blood_number = 0;
        public Canvas canvas = null;
        public Bitmap bitmap = null;
        public holder(){
            canvas = new Canvas();
        }
        public void init(Bitmap bitmap){
            if(bitmap == null )throw new NullPointerException("Where is bitmap for holder init");
            this.bitmap = bitmap;
            if(canvas!=null){
                canvas.setBitmap(bitmap);
            }
            blood_number = 0;
        }
    }

    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w    Current width of this view.
     * @param h    Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initDrawConfig();
        if (mHolder.bitmap != null) {
            mHolder.bitmap.recycle();
        }
        mHolder.init(Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888));
    }

    public void clean(){
        if (mHolder.bitmap != null) {
            mHolder.init(Bitmap.createBitmap( mHolder.bitmap.getWidth(), mHolder.bitmap.getWidth(), Bitmap.Config.ARGB_8888));
        }
    }
}
