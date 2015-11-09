package go.deyu.dailyphoneuse;

/**
 * Created by huangeyu on 15/4/28.
 */
public interface BloodModelInterface {
    public long getBlood();
    public long getUseTime();
    public void setUseTime(long time);
    public void incrementUseTime();
    public void setmListener(BloodModel.OnCountChangeListener mListener);

}
