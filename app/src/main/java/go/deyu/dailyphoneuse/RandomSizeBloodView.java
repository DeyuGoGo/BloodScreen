package go.deyu.dailyphoneuse;

import android.content.Context;
import android.util.AttributeSet;


/**
 * TODO: document your custom view class.
 */
public class RandomSizeBloodView extends BloodView {

    private int mBloodRadiusSizeMAX = 80;

    public RandomSizeBloodView(Context context) {
        super(context);
    }

    public RandomSizeBloodView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RandomSizeBloodView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getBloodRadius() {
        return mRandom.nextInt(mBloodRadiusSizeMAX)+1;
    }

}
