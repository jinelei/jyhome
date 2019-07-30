package cn.jinelei.jyhome.page.base.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import cn.jinelei.jyhome.R;

public class NestedScrollingLayout extends LinearLayout implements NestedScrollingParent2 {
    private static final String TAG = "NestedScrollingLayout";
    private int mLastY;
    private boolean isHeadHide;
    private ImageView mHeadView;
    private ViewPager mViewPager;
    private int mHeadTopHeight;

    public NestedScrollingLayout(Context context) {
        this(context, null);
    }

    public NestedScrollingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public NestedScrollingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent: ACTION_DOWN");
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent: ACTION_MOVE");
                int dy = mLastY - y;
                //如果父控件拦截，根据传统事件传递机制，如果父控件确定拦截事件，那么在同一事件序列中，子控件是没有办法获取到事件的。
                if (Math.abs(dy) > ViewConfiguration.getTouchSlop()) {
                    if (dy > 0 && !isHeadHide) { //如果是向上滑，且当前headView没有隐藏，那么就拦截
                        Log.d(TAG, "onInterceptTouchEvent: 开始向上拦截");
                        return true;
                    } else if (dy < 0 && isHeadHide) {//如果是向下, 且将headView已经隐藏，那么就拦截
                        Log.d(TAG, "onInterceptTouchEvent: 开始向下拦截");
                        return true;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(event);//不拦截事件，把事件让给子控件。
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: ACTION_MOVE");
                int dy = mLastY - y;
                if (Math.abs(dy) > ViewConfiguration.getTouchSlop()) {
                    scrollBy(0, dy);
                }
                mLastY = y;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollTo(int x, int y) {
        Log.d(TAG, "scrollTo: " + x + "  " + y);
        if (y < 0) {
            y = 0;
        }
        if (y > mHeadTopHeight) {
            y = mHeadTopHeight;
        }
        super.scrollTo(x, y);
        isHeadHide = getScrollY() == mHeadTopHeight;//判断当前head是否已经隐藏了
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
        //ViewPager修改后的高度= 总高度-导航栏高度
        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
        layoutParams.height = getHeight() - mHeadView.getHeight();
        mViewPager.setLayoutParams(layoutParams);
        //当ViewPager修改高度后，重新开始测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged");
        mHeadTopHeight = mHeadView.getMeasuredHeight();//获取headView高度
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
        mHeadView = findViewById(R.id.iv_home_header);
        mViewPager = findViewById(R.id.vp_home);
        if (!(mViewPager instanceof ViewPager)) {
            throw new RuntimeException("id view_pager should be viewpager!");
        }
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.d(TAG, "onStartNestedScroll");
        if (axes == ViewCompat.SCROLL_AXIS_VERTICAL)
            return true;
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.d(TAG, "onNestedScrollAccepted");
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        Log.d(TAG, "onStopNestedScroll");
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.d(TAG, "onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.d(TAG, "onNestedPreScroll");
        if ((dy > 0 && getScrollY() < mHeadView.getHeight()) ||
                (dy < 0 && getScrollY() > 0)) {
            consumed[1] = dy;
            scrollBy(dx, dy);
        }
    }
}
