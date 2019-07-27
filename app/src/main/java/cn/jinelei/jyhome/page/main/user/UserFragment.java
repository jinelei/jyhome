package cn.jinelei.jyhome.page.main.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Date;
import java.util.Random;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.model.User;
import cn.jinelei.jyhome.page.base.BaseFragment;
import cn.jinelei.jyhome.page.base.feature.ISilenceLoading;

public class UserFragment extends BaseFragment implements ISilenceLoading {
    private static final String TAG = "UserFragment";
    private final Random random = new Random();
    private UserViewModel userViewModel;
    private TextView tvHeight;
    private TextView tvGender;
    private TextView tvWeight;
    private TextView tvBirth;
    private SpinKitView skvSilenceLoading;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    private View.OnClickListener mUserInfoOnClickListener = view -> {
        switch (view.getId()) {
            case R.id.tv_height:
            case R.id.cl_height:
                userViewModel.updateUser(User.FIELD_HEIGHT, random.nextDouble());
                break;
            case R.id.tv_weight:
            case R.id.cl_weight:
                userViewModel.updateUser(User.FIELD_WEIGHT, random.nextDouble());
                break;
            case R.id.tv_birth:
            case R.id.cl_birth:
                userViewModel.updateUser(User.FIELD_BIRTH, random.nextLong());
                break;
            case R.id.tv_gender:
            case R.id.cl_gender:
                userViewModel.updateUser(User.FIELD_GENDER, random.nextInt(2));
                break;
        }
    };

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
        tvHeight = view.findViewById(R.id.tv_height);
        tvWeight = view.findViewById(R.id.tv_weight);
        tvBirth = view.findViewById(R.id.tv_birth);
        tvGender = view.findViewById(R.id.tv_gender);
        skvSilenceLoading = view.findViewById(R.id.skv_nav_loading);
        view.findViewById(R.id.cl_height).setOnClickListener(mUserInfoOnClickListener);
        view.findViewById(R.id.cl_weight).setOnClickListener(mUserInfoOnClickListener);
        view.findViewById(R.id.cl_gender).setOnClickListener(mUserInfoOnClickListener);
        view.findViewById(R.id.cl_birth).setOnClickListener(mUserInfoOnClickListener);
    }

    @Override
    public void initEvent() {
        Log.d(TAG, "initEvent");
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.userData.observe(this, user -> {
            hideSilenceLoading();
            tvBirth.setText(mBaseApplication.sdfyyyyMMdd_hhmmss.format(new Date(user.getBirth())));
            tvGender.setText(user.getGender().getName());
            tvHeight.setText(String.format("%.02f", user.getHeight()));
            tvWeight.setText(String.format("%.02f", user.getWeight()));
        });
        mHandler.postDelayed(() -> {
            showSilenceLoading();
            userViewModel.getUser();
        }, 3000);
    }

    @Override
    public void showSilenceLoading() {
        skvSilenceLoading.setVisibility(View.VISIBLE);
        skvSilenceLoading.setIndeterminate(true);
    }

    @Override
    public void hideSilenceLoading() {
        skvSilenceLoading.setVisibility(View.GONE);
        skvSilenceLoading.setIndeterminate(false);
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
