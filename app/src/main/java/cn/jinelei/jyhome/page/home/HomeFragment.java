package cn.jinelei.jyhome.page.home;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.page.base.BaseFragment;

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.d(TAG, "HomeFragment inflate");
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    protected void initAllMembersView(Bundle saveInstanceState) {

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
