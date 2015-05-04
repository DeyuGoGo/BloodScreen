package go.deyu.bloodscreen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
    private int m_nScreenW , m_nScreenH ;
    private Paint mBloodPaint ;
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
        mRandom = new Random();
        mBloodPaint = new Paint();
        mBloodPaint.setColor(Color.RED);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        initDrawConfig();
        drawBloods(canvas);
        super.onDraw(canvas);
    }


    private void initDrawConfig(){
        WindowManager winMgr = (WindowManager)getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE) ;
        Point size = new Point();
        winMgr.getDefaultDisplay().getSize(size);
        m_nScreenW = size.x;
        m_nScreenH = size.y;
        LOG.d(TAG, "m_nScreenW: " + m_nScreenW + " m_nScreenH : " + m_nScreenH);
    }

    private void drawBloods(Canvas canvas){
        for(int i = 0, len = app.App.model.getBlood(); i < len; i++){
            drawBlood(canvas);
        }
    }
    
    private void drawBlood(Canvas canvas){
        int size = getBloodRadius();
        int x = mRandom.nextInt((m_nScreenW));
        int y = mRandom.nextInt((m_nScreenH));
        canvas.drawCircle(x, y, size , mBloodPaint);
    }

    protected int getBloodRadius(){
        return defaultBloodRadius;
    }

// no use
    class holder{
        public int blood_number ;
        public Canvas canvas;
        public holder(Canvas canvas , int number){
            this.canvas = canvas ;
            this.blood_number = number;
        }
    }

}
