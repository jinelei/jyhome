package cn.jinelei.jyhome.page.main.test;


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
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.page.base.BaseFragment;
import cn.jinelei.jyhome.page.base.feature.ISilenceLoading;

public class TestFragment extends BaseFragment implements ISilenceLoading {
    private static final String TAG = "TestFragment";
    private TestViewModel testViewModel;
    private ImageView ivNavBarRight;
    private ImageView ivNavBarLeft;
    private SpinKitView skvNavBarLoading;
    private TextView tvMessage;

    private final View.OnClickListener clickListener = view -> {
        showSilenceLoading();
        switch (view.getId()) {
            case R.id.btn_success:
                testViewModel.getTestData("success");
                break;
            case R.id.btn_failure:
                testViewModel.getTestData("failure");
                break;
            case R.id.btn_complete:
                testViewModel.getTestData("complete");
                break;
            case R.id.btn_error:
                testViewModel.getTestData("error");
                break;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        initView(rootView);
        initEvent();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        testViewModel.testData.observe(this, s -> {
            hideSilenceLoading();
            tvMessage.append(s + "\n");
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void initView(View view) {
        Log.d(TAG, "initView: " + view.toString());
        view.findViewById(R.id.btn_success).setOnClickListener(clickListener);
        view.findViewById(R.id.btn_failure).setOnClickListener(clickListener);
        view.findViewById(R.id.btn_error).setOnClickListener(clickListener);
        view.findViewById(R.id.btn_complete).setOnClickListener(clickListener);
        View navBar = view.findViewById(R.id.nav_bar);
        skvNavBarLoading = navBar.findViewById(R.id.skv_nav_loading);
        ivNavBarRight = navBar.findViewById(R.id.iv_nav_right);
        ivNavBarLeft = navBar.findViewById(R.id.iv_nav_left);
//        ((TextView) navBar.findViewById(R.id.tv_nav_title)).setText(R.string.navigation_home);
        tvMessage = view.findViewById(R.id.tv_message);
    }

    @Override
    public void initEvent() {
        Log.d(TAG, "initEvent");
    }

    @Override
    public void showSilenceLoading() {
        skvNavBarLoading.setVisibility(View.VISIBLE);
        Sprite doubleBounce = new DoubleBounce();
        skvNavBarLoading.setIndeterminateDrawable(doubleBounce);
    }

    @Override
    public void hideSilenceLoading() {
        skvNavBarLoading.setVisibility(View.GONE);
        Sprite doubleBounce = new DoubleBounce();
        skvNavBarLoading.setIndeterminateDrawable(doubleBounce);
    }
}
