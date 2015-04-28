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
    private final String TAG = getClass().getSimpleName();

    private holder mHolder = null;
    private int m_nScreenW , m_nScreenH ;
    private Paint mBloodPaint ;

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
        for(int i = 0 ; i < app.App.model.getBlood() ; i++){
            drawBlood(canvas);
        }
    }
    private void drawBlood(Canvas canvas){
        Random random = new Random();
        int x = random.nextInt((m_nScreenW - 2));
        int y = random.nextInt((m_nScreenH - 2));
        canvas.drawCircle(x, y, 2, mBloodPaint);
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
