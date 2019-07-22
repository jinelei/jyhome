package cn.jinelei.jyhome.page.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.page.TestFragment;
import cn.jinelei.jyhome.page.base.BaseFragment;

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private ViewPager viewPager;
    private final ArrayList<Fragment> allFragments = new ArrayList<>();

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.v(TAG, String.format("onPageScrolled position: %d positionOffset: %f positionOffsetPixels: %d", position, positionOffset, positionOffsetPixels));
        }

        @Override
        public void onPageSelected(int position) {
            Log.v(TAG, "onPageSelected " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.v(TAG, "onPageScrollStateChanged " + state);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView(rootView);
        initEvent();
        return rootView;
    }

    @Override
    public void initView(View view) {
        viewPager = view.findViewById(R.id.vp_home);
    }

    @Override
    public void initEvent() {
        for (int i = 0; i < 5; i++) {
            allFragments.add(new TestFragment());
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return allFragments.get(position % allFragments.size());
            }

            @Override
            public int getCount() {
                return allFragments.size();
            }
        });
        viewPager.setOnPageChangeListener(onPageChangeListener);
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
