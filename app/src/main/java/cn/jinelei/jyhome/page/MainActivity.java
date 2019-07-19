package cn.jinelei.jyhome.page;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.page.base.BaseActivity;
import cn.jinelei.jyhome.page.discovery.DiscoveryFragment;
import cn.jinelei.jyhome.page.home.HomeFragment;
import cn.jinelei.jyhome.page.user.UserFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static Fragment currentFragment;
    private BottomNavigationView mBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                switchFragmentTo(R.id.frame_main, HomeFragment.Singleton.INSTANCE.getInstance());
            case R.id.navigation_user:
                switchFragmentTo(R.id.frame_main, UserFragment.Singleton.INSTANCE.getInstance());
            case R.id.navigation_discovery:
                switchFragmentTo(R.id.frame_main, DiscoveryFragment.Singleton.INSTANCE.getInstance());
            default:
                Log.e(TAG, "invalid switch fragment: " + menuItem.getItemId());
        }
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        mBottomNavigationView = findViewById(R.id.bnv_main);
    }

    @Override
    public void initEvent() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    @Override
    public void initData() {
        switchFragmentTo(R.id.frame_main, HomeFragment.Singleton.INSTANCE.getInstance());
    }

    public void switchFragmentTo(@IdRes int containerId, @NotNull Fragment targetFragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (currentFragment == null) {
            Log.d(TAG, String.format("%s to %s", "null", targetFragment.getClass().getSimpleName()));
            if (targetFragment.isAdded()) {
                supportFragmentManager.beginTransaction().show(targetFragment).commit();
            } else {
                supportFragmentManager.beginTransaction().add(containerId, targetFragment).commit();
            }
        } else if (currentFragment != targetFragment) {
            Log.d(TAG, String.format("%s to %s", currentFragment.getClass().getSimpleName(), targetFragment.getClass().getSimpleName()));
            if (targetFragment.isAdded()) {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(targetFragment).commit();
            } else {
                supportFragmentManager.beginTransaction().hide(currentFragment).add(containerId, targetFragment).commit();
            }
        }
        currentFragment = targetFragment;
    }

}
