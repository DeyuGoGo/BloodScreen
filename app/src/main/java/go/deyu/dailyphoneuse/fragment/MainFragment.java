package go.deyu.dailyphoneuse.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import go.deyu.dailyphoneuse.DrawService;
import go.deyu.dailyphoneuse.R;
import go.deyu.dailyphoneuse.SettingConfig;
import go.deyu.dailyphoneuse.app.app;
import go.deyu.util.TimeUtil;

/**
 * Created by huangeyu on 15/11/3.
 */
public class MainFragment extends BaseFragment implements OnChartValueSelectedListener {

    private Timer mUseTimer = null;
    private long mTodayUseTime = 0 , mPercent = 0 , mTodayPassTime = 0;
    private Context mContext;
    private String[] imgs ;

    @Bind(R.id.switch_love_open)Switch mSwitchLove;

    @Bind(R.id.switch_count_phone_open)Switch mSwitchCount;

    @Bind(R.id.main_pie_chart)PieChart mChart;

    @Bind(R.id.spinner_image)Spinner mImgsSpinner;

    @Bind(R.id.fl_desktop_type_item)FrameLayout mImageTypeItem;

    @BindString(R.string.use)String strUse;
    @BindString(R.string.no_use)String strNoUse;

    @OnClick(R.id.main_btn_clean)
    public void clean(){
        Intent i = new Intent(getActivity(),DrawService.class);
        i.setAction(DrawService.ACTION_CLEAN);
        getActivity().startService(i);
    }

    private static final int WHAT_UPDATE_TODAY_USE_TIME = 0x02;

    private Handler UiHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHAT_UPDATE_TODAY_USE_TIME:
                    setData(mPercent);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        mSwitchLove.setChecked(SettingConfig.getIsLoveOpen(mContext));
        mSwitchLove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingConfig.setIsLoveOpen(mContext, isChecked);
                setLoveVisible(isChecked);
                mImageTypeItem.setVisibility(isChecked? View.VISIBLE:View.GONE);
            }
        });
        mSwitchCount.setChecked(SettingConfig.getIsCountingOpen(mContext));
        mSwitchCount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingConfig.setIsCountingOpen(mContext, isChecked);
                setCountingStart(isChecked);
            }
        });
        setupTimeData();
        setupPieChart();
        setupSpinner();
        startUpdateTodayUseTimer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelUpdateTodayUseTimer();
    }

    private void startUpdateTodayUseTimer(){
        if(mUseTimer == null) {
            mUseTimer = new Timer();
            mUseTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    setupTimeData();
                    UiHandler.sendEmptyMessage(WHAT_UPDATE_TODAY_USE_TIME);
                }
            }, 1, 1 * 1000);
        }
    }

    private void cancelUpdateTodayUseTimer(){
        if(mUseTimer != null){
            mUseTimer.cancel();
            mUseTimer = null;
        }
    }

    private void setLoveVisible(boolean visible){
        Intent i = new Intent(getActivity(),DrawService.class);
        if(visible)
            i.setAction(DrawService.ACTION_SHOW);
        else
            i.setAction(DrawService.ACTION_NOT_SHOW);
        getActivity().startService(i);
    }

    private void setupTimeData(){
        mTodayUseTime = app.App.model.getUseTime() ;
        setupPercent();
    }

    private void setupPercent(){
        if(mTodayUseTime<=0){
            mPercent =0 ;
            return;
        }
        Calendar c = Calendar.getInstance();
        int nowHour = c.get(Calendar.HOUR_OF_DAY);
        int nowMin = c.get(Calendar.MINUTE);
        int nowSecond = c.get(Calendar.SECOND);
        mTodayPassTime = (nowHour*60*60) + (nowMin*60) + nowSecond;
        mPercent = ((mTodayUseTime*100 )/ mTodayPassTime);
        if(mPercent > 100 ) mPercent = 100;
    }

    private void setCountingStart(boolean start){
        Intent i = new Intent(getActivity(),DrawService.class);
        if(start)
            i.setAction(DrawService.ACTION_START_ADD_TIMER);
        else
            i.setAction(DrawService.ACTION_CANCEL_ADD_TIMER);
        getActivity().startService(i);
    }

    private void setupPieChart(){
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setCenterText(generateCenterSpannableText());
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);
        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);
        setData(mPercent);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private SpannableString generateCenterSpannableText() {
        String[] des;
        int id = 0;
        if(mPercent>40)
            id = R.array.high_user;
        else if(mPercent>20)
            id = R.array.mid_user;
        else id = R.array.low_user;
        des = getResources().getStringArray(id);
        Random r = new Random();
        SpannableString s = new SpannableString(des[r.nextInt(des.length)]);
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length() , 0);
        return s;
    }

    private void setupSpinner(){
        mImageTypeItem.setVisibility(SettingConfig.getIsLoveOpen(mContext) ? View.VISIBLE : View.GONE);
        imgs = getActivity().getResources().getStringArray(R.array.imgs);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item , imgs);
        mImgsSpinner.setAdapter(stringArrayAdapter);
        mImgsSpinner.setSelection(SettingConfig.getImageType(getActivity()));
        mImgsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Position :" + position + "Select : " + imgs[position], Toast.LENGTH_SHORT).show();
                SettingConfig.setImageType(mContext, position);
                Intent i = new Intent(mContext, DrawService.class);
                i.setAction(DrawService.ACTION_REFRESH_VIEW);
                mContext.startService(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setData(long phoneusePercent) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry((float)phoneusePercent,0));
        yVals1.add(new Entry((float)(100 - phoneusePercent),1));

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add(strUse+ TimeUtil.secondToHMS(mTodayUseTime));
        xVals.add(strNoUse + TimeUtil.secondToHMS(mTodayPassTime-mTodayUseTime));
        PieDataSet dataSet = new PieDataSet(yVals1, "Use state");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);


        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
