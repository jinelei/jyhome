package cn.jinelei.jyhome.page.main.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.page.base.BaseFragment;
import cn.jinelei.jyhome.page.base.feature.ISilenceLoading;
import cn.jinelei.jyhome.page.main.home.test.TestFragment;

public class HomeFragment extends BaseFragment implements ISilenceLoading {
    private static final String TAG = "HomeFragment";
    private ViewPager viewPager;
    private TextView tvTitle;
    private ImageView ivNavLeft;
    private ImageView ivNavRight;
    private SpinKitView skvNavLoading;
    private final ArrayList<Fragment> allFragments = new ArrayList<>();

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d(TAG, String.format("onPageScrolled position: %d positionOffset: %f positionOffsetPixels: %d", position, positionOffset, positionOffsetPixels));
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG, "onPageScrollStateChanged " + state);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView(rootView);
        initEvent();
        return rootView;
    }

    @Override
    public void initView(View view) {
        Log.d(TAG, "initView");
        viewPager = view.findViewById(R.id.vp_home);
        View navBar = view.findViewById(R.id.nav_bar);
        tvTitle = navBar.findViewById(R.id.tv_nav_title);
        ivNavLeft = navBar.findViewById(R.id.iv_nav_left);
        ivNavRight = navBar.findViewById(R.id.iv_nav_right);
        skvNavLoading = navBar.findViewById(R.id.skv_nav_loading);
        tvTitle.setText(R.string.navigation_home);
    }

    @Override
    public void initEvent() {
        Log.d(TAG, "initEvent");
        for (int i = 0; i < 5; i++) {
            allFragments.add(new TestFragment());
        }
        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            private int mRealSize = allFragments.size();

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return allFragments.get(position % mRealSize);
            }

            @Override
            public int getCount() {
                return mRealSize;
            }
        });
        viewPager.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void showSilenceLoading() {
        skvNavLoading.setVisibility(View.VISIBLE);
        skvNavLoading.setIndeterminate(true);
    }

    @Override
    public void hideSilenceLoading() {
        skvNavLoading.setVisibility(View.GONE);
        skvNavLoading.setIndeterminate(false);
    }


    public enum Singleton implements JySingleton<HomeFragment> {
        INSTANCE {
            public HomeFragment instance = new HomeFragment();

            @Override
            public HomeFragment getInstance() {
                return instance;
            }
        }
    }
}
