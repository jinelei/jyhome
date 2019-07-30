package cn.jinelei.jyhome.page.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.page.base.BaseActivity;
import cn.jinelei.jyhome.page.main.discovery.DiscoveryFragment;
import cn.jinelei.jyhome.page.main.home.HomeFragment;
import cn.jinelei.jyhome.page.main.user.UserFragment;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MainActivity";
    private static final int REFRESH_COMPLETE = 1001;
    private Fragment currentFragment = HomeFragment.Singleton.INSTANCE.getInstance();
    private BottomNavigationView mBottomNavigationView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = menuItem -> {
        Log.d(TAG, "OnNavigationItemSelected: " + menuItem.getItemId());
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                switchFragmentTo(R.id.frame_main, HomeFragment.Singleton.INSTANCE.getInstance());
                break;
            case R.id.navigation_user:
                switchFragmentTo(R.id.frame_main, UserFragment.Singleton.INSTANCE.getInstance());
                break;
            case R.id.navigation_discovery:
                switchFragmentTo(R.id.frame_main, DiscoveryFragment.Singleton.INSTANCE.getInstance());
                break;
            default:
                Log.e(TAG, "invalid switch fragment: " + menuItem.getItemId());
        }
        return true;
    };
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void initView() {
        Log.d(TAG, "initView");
        setContentView(R.layout.activity_main);
        mBottomNavigationView = findViewById(R.id.bnv_main);
        mSwipeRefreshLayout = findViewById(R.id.swl_main);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    @Override
    public void initEvent() {
        Log.d(TAG, "initEvent");
        mBottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_main, currentFragment).commit();
        checkAndSetRefreshEnabled();
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void switchFragmentTo(@IdRes int containerId, @NotNull Fragment targetFragment) {
        Log.d(TAG, "switchFragmentTo: from " + currentFragment + " to " + targetFragment.getTag());
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (!currentFragment.equals(targetFragment)) {
            Log.d(TAG, String.format("switchFragmentTo %s to %s", currentFragment.getClass().getSimpleName(), targetFragment.getClass().getSimpleName()));
            if (targetFragment.isAdded()) {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(targetFragment).commit();
            } else {
                supportFragmentManager.beginTransaction().hide(currentFragment).add(containerId, targetFragment).commit();
            }
            currentFragment = targetFragment;
            checkAndSetRefreshEnabled();
        }
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

    public void setEnablePullRefresh(boolean enable) {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setEnabled(enable);
    }

    private void checkAndSetRefreshEnabled() {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
        if (currentFragment instanceof HomeFragment) {
            setEnablePullRefresh(false);
        } else {
            setEnablePullRefresh(true);
        }
    }
}
