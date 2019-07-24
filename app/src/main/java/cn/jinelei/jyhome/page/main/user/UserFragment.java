package cn.jinelei.jyhome.page.main.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.github.ybq.android.spinkit.SpinKitView;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.page.base.BaseFragment;
import cn.jinelei.jyhome.page.base.feature.ISilenceLoading;

public class UserFragment extends BaseFragment implements ISilenceLoading {
    private static final String TAG = "UserFragment";
    private TextView tvTest;
    private UserViewModel userViewModel;
    private TextView tvTitle;
    private ImageView ivNavLeft;
    private ImageView ivNavRight;
    private SpinKitView skvNavLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        initView(rootView);
        initEvent();
        return rootView;
    }

    @Override
    public void initView(View view) {
        Log.d(TAG, "initView: " + view.toString());
        tvTest = view.findViewById(R.id.tv_test);
        View navBar = view.findViewById(R.id.nav_bar);
        tvTitle = navBar.findViewById(R.id.tv_nav_title);
        ivNavLeft = navBar.findViewById(R.id.iv_nav_left);
        ivNavRight = navBar.findViewById(R.id.iv_nav_right);
        skvNavLoading = navBar.findViewById(R.id.skv_nav_loading);
        tvTitle.setText(R.string.navigation_user);
    }

    @Override
    public void initEvent() {
        Log.d(TAG, "initEvent");
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.userData.observe(this, user -> {
            hideSilenceLoading();
            tvTest.setClickable(true);
            tvTest.setText(user.toString());
        });
        tvTest.setOnClickListener(v -> {
            Log.d(TAG, "initEvent: getUser");
            showSilenceLoading();
            tvTest.setClickable(false);
            userViewModel.getUser();
        });
    }

    @Override
    public void showSilenceLoading() {
        skvNavLoading.setVisibility(View.VISIBLE);
        skvNavLoading.setIndeterminate(true);
    }

    @Override
    public void hideSilenceLoading() {
        skvNavLoading.setVisibility(View.GONE);
    }

    public enum Singleton implements JySingleton<UserFragment> {
        INSTANCE {
            public UserFragment instance = new UserFragment();

            @Override
            public UserFragment getInstance() {
                return instance;
            }
        }
    }
}
