package cn.jinelei.jyhome.page.main.home.test;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import java.util.Optional;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.page.base.BaseFragment;
import cn.jinelei.jyhome.page.main.MainActivity;
import cn.jinelei.jyhome.page.main.home.HomeFragment;

public class TestFragment extends BaseFragment {
    private static final String TAG = "TestFragment";
    private TestViewModel testViewModel;
    private TextView tvMessage;

    private final View.OnClickListener clickListener = view -> {
        Optional.ofNullable(mContext)
                .filter(c -> c instanceof MainActivity)
                .flatMap(c -> Optional.ofNullable(((MainActivity) c).getCurrentFragment()))
                .filter(fragment -> fragment instanceof HomeFragment)
                .ifPresent(fragment -> ((HomeFragment) fragment).showSilenceLoading(HomeFragment.Singleton.INSTANCE.getInstance()));
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
            Optional.ofNullable(mContext)
                    .filter(c -> c instanceof MainActivity)
                    .flatMap(c -> Optional.ofNullable(((MainActivity) c).getCurrentFragment()))
                    .filter(fragment -> fragment instanceof HomeFragment)
                    .ifPresent(fragment -> ((HomeFragment) fragment).hideSilenceLoading(HomeFragment.Singleton.INSTANCE.getInstance()));
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
        tvMessage = view.findViewById(R.id.tv_message);
    }

    @Override
    public void initEvent() {
        Log.d(TAG, "initEvent");
    }

}
