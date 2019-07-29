package cn.jinelei.jyhome.page.main.user.component;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.WeakHashMap;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.BaseApplication;
import cn.jinelei.jyhome.page.base.feature.ICallback;

public class BirthPopupWindow extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "BirthPopupWindow";
    private final View rootView;
    private final ICallback<Calendar> callback;
    private Context mContext;
    private WheelView<String> firstWheelView;
    private WheelView<String> secondWheelView;
    private WheelView<String> thirdWheelView;
    private ArrayList<String> mYearList = new ArrayList<>();
    private ArrayList<String> mMonthList;
    private ArrayList<String> mDayList;
    private Calendar mSelectCalendar = Calendar.getInstance();
    private final WeakHashMap<String, ArrayList<String>> cacheGenNumberSeq = new WeakHashMap<>();

    public BirthPopupWindow(Context context, ICallback<Calendar> callback) {
        super(context);
        Log.d(TAG, "BirthPopupWindow");
        this.mContext = context;
        this.callback = callback;
        LayoutInflater inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(R.layout.popup_window_gender, null);
        setContentView(rootView);
        rootView.findViewById(R.id.ll_btn_container).findViewById(R.id.btnConfirm).setOnClickListener(this);
        initView(rootView);
        initEvent();
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        firstWheelView = view.findViewById(R.id.first_wheelview);
        secondWheelView = view.findViewById(R.id.second_wheelview);
        thirdWheelView = view.findViewById(R.id.third_wheelview);

        firstWheelView.setWheelAdapter(new StringWheelViewAdapter(mContext));
        firstWheelView.setSkin(WheelView.Skin.Holo);

        secondWheelView.setWheelAdapter(new StringWheelViewAdapter(mContext));
        secondWheelView.setSkin(WheelView.Skin.Holo);

        thirdWheelView.setWheelAdapter(new StringWheelViewAdapter(mContext));
        thirdWheelView.setSkin(WheelView.Skin.Holo);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;
        style.selectedTextColor = Color.parseColor("#FFFFFF");
        style.textColor = Color.parseColor("#808080");
        style.backgroundColor = Color.parseColor("#2B2B2B");
        style.holoBorderColor = Color.parseColor("#383838");

        firstWheelView.setStyle(style);
        secondWheelView.setStyle(style);
        thirdWheelView.setStyle(style);

        //设置动画效果
        this.setAnimationStyle(R.style.popup_window_anim);

        initEvent();
        initWheelData(Calendar.YEAR);
        resetView();
    }

    private void initWheelData(int field) {
        Log.d(TAG, String.format("initWheelData: field: %d %s", field, BaseApplication.sdf_yyyy_MM_dd.format(mSelectCalendar.getTime())));
        Calendar now = Calendar.getInstance();
        switch (field) {
            case Calendar.YEAR:
                mYearList = genNumberSeq(1970, now.get(Calendar.YEAR));
                break;
            case Calendar.MONTH:
                int monthLimit = now.get(Calendar.YEAR) == mSelectCalendar.get(Calendar.YEAR)
                        ? now.get(Calendar.MONTH) + 1 : 12;
                mMonthList = genNumberSeq(1, monthLimit);
                break;
            case Calendar.DAY_OF_MONTH:
                int dayLimit = (mSelectCalendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) && mSelectCalendar.get(Calendar.MONTH) == now.get(Calendar.MONTH))
                        ? now.get(Calendar.DATE) : mSelectCalendar.getActualMaximum(Calendar.DATE);
                mDayList = genNumberSeq(1, dayLimit);
                break;
        }
    }

    private void initEvent() {
        Log.d(TAG, "initEvent");
        firstWheelView.setOnWheelItemSelectedListener((position, s) -> {
            try {
                if (s.equals(String.valueOf(mSelectCalendar.get(Calendar.YEAR))))
                    return;
                mSelectCalendar.set(Calendar.YEAR, Integer.parseInt(s));
                initWheelData(Calendar.MONTH);
                secondWheelView.resetDataFromTop(mMonthList);
                initWheelData(Calendar.DAY_OF_MONTH);
                thirdWheelView.resetDataFromTop(mDayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        secondWheelView.setOnWheelItemSelectedListener((position, s) -> {
            try {
                if (s.equals(String.valueOf(mSelectCalendar.get(Calendar.MONTH) + 1)))
                    return;
                mSelectCalendar.set(Calendar.MONTH, Integer.parseInt(s) - 1);
                initWheelData(Calendar.DAY_OF_MONTH);
                thirdWheelView.resetDataFromTop(mDayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thirdWheelView.setOnWheelItemSelectedListener((position, s) -> {
            try {
                if (s.equals(String.valueOf(mSelectCalendar.get(Calendar.DAY_OF_MONTH))))
                    return;
                mSelectCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void resetView() {
        Log.d(TAG, "resetView");
        initWheelData(Calendar.MONTH);
        initWheelData(Calendar.DAY_OF_MONTH);
        firstWheelView.setWheelData(mYearList);
        secondWheelView.setWheelData(mMonthList);
        thirdWheelView.setWheelData(mDayList);
        int yearIdx = mYearList.indexOf(String.valueOf(mSelectCalendar.get(Calendar.YEAR)));
        firstWheelView.setSelection(yearIdx > 0 ? yearIdx : 0);
        int monthIdx = mMonthList.indexOf(String.valueOf(mSelectCalendar.get(Calendar.MONTH) + 1));
        secondWheelView.setSelection(monthIdx > 0 ? monthIdx : 0);
        int dayIdx = mDayList.indexOf(String.valueOf(mSelectCalendar.get(Calendar.DAY_OF_MONTH)));
        thirdWheelView.setSelection(dayIdx > 0 ? dayIdx : 0);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: " + view.getId());
        switch (view.getId()) {
            case R.id.btnConfirm:
                if (this.callback != null) {
                    this.callback.onSuccess(mSelectCalendar);
                }
                break;
            default:
                break;
        }
    }

    private ArrayList<String> genNumberSeq(int start, int end) {
        String key = start + "_" + end;
        if (cacheGenNumberSeq.containsKey(key)) {
            Log.d(TAG, "genNumberSeq: cached " + key);
            return cacheGenNumberSeq.get(key);
        }
        ArrayList<String> strings = new ArrayList<>();
        while (start <= end)
            strings.add(String.valueOf(start++));
        cacheGenNumberSeq.put(key, strings);
        Log.d(TAG, "genNumberSeq: new " + key);
        return strings;
    }
}
