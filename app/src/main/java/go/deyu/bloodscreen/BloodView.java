package go.deyu.bloodscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import go.deyu.bloodscreen.BloodBitmapFactory.BloodBitmapFactory;
import go.deyu.bloodscreen.BloodBitmapFactory.ResourceBloodBitmapFactory;
import go.deyu.bloodscreen.app.app;
import go.deyu.util.LOG;


/**
 * TODO: document your custom view class.
 */
public class BloodView extends View {

    protected Random mRandom;

    private final String TAG = getClass().getSimpleName();

    private holder mHolder = null;
    private int defaultBloodRadius = 2;
    private BloodBitmapFactory mBloodBitmapFactory = null;


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
        mBloodBitmapFactory = new ResourceBloodBitmapFactory(getContext());
    }

    public void setBloodBitmapFactory(BloodBitmapFactory factory){
        this.mBloodBitmapFactory = factory;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        LOG.d(TAG, "OnDraw");
        drawBloods(mHolder.canvas);
        Bitmap bitmap = mHolder.bitmap;
        LOG.d(TAG,"OnDraw : bitmap.getWidth() : " + bitmap.getWidth());
        canvas.drawBitmap(bitmap,
                new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), null);
    }

    private void initDrawConfig(int w , int h) {
        if (mHolder.bitmap != null) {
            mHolder.bitmap.recycle();
        }
        mHolder.init(Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888));
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
        int x = mRandom.nextInt((mHolder.maxX));
        int y = mRandom.nextInt((mHolder.maxY));
        Bitmap bitmap = mBloodBitmapFactory.createBloodBitmap(size,size);
        LOG.d(TAG, "getBloodRadius size : " + size + " x : " + x + " y : " + y);
        canvas.drawBitmap(bitmap, x, y, null);
    }

    protected int getBloodRadius() {
        return defaultBloodRadius;
    }

    class holder {
        public int blood_number = 0;
        public Canvas canvas = null;
        public Bitmap bitmap = null;
        public int maxX;
        public int maxY;
        public holder(){
            canvas = new Canvas();
        }

        public void init(Bitmap bitmap){
            if(bitmap == null )throw new NullPointerException("Where is bitmap for holder init");
            this.bitmap = bitmap;
            if(canvas!=null){
                canvas.setBitmap(bitmap);
            }
            maxX = this.bitmap.getWidth();
            maxY = this.bitmap.getHeight();
            LOG.d(TAG,"maxX:" + maxX);
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
        initDrawConfig(w, h);
        LOG.d(TAG, "onSizeChanged" + "w: " + w + " h : " + h);
    }

    public void clean(){
        if (mHolder.bitmap != null) {
            mHolder.init(Bitmap.createBitmap( mHolder.bitmap.getWidth(), mHolder.bitmap.getWidth(), Bitmap.Config.ARGB_8888));
        }
    }
}