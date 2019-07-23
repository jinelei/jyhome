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
    private static final String TAG = "MainActivity";
    private Fragment currentFragment = HomeFragment.Singleton.INSTANCE.getInstance();
    private BottomNavigationView mBottomNavigationView;

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
    }

    @Override
    public void initEvent() {
        Log.d(TAG, "initEvent");
        mBottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_main, currentFragment).commit();
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
        }
    }

}
