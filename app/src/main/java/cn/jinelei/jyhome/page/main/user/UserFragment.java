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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Date;
import java.util.Random;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.model.User;
import cn.jinelei.jyhome.page.base.BaseFragment;

public class UserFragment extends BaseFragment {
    private static final String TAG = "UserFragment";
    private final Random random = new Random();
    private UserViewModel userViewModel;
    private TextView tvHeight;
    private TextView tvGender;
    private TextView tvWeight;
    private TextView tvBirth;
    private ConstraintLayout clHeight;
    private ConstraintLayout clWeight;
    private ConstraintLayout clBirth;
    private ConstraintLayout clGender;
    private SpinKitView skvSilenceLoading;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    private View.OnClickListener mUserInfoOnClickListener = view -> {
        switch (view.getId()) {
            case R.id.cl_height:
                showSilenceLoading(UserFragment.this);
                view.setClickable(false);
                userViewModel.updateUser(User.FIELD_HEIGHT, random.nextDouble());
                break;
            case R.id.cl_weight:
                showSilenceLoading(UserFragment.this);
                view.setClickable(false);
                userViewModel.updateUser(User.FIELD_WEIGHT, random.nextDouble());
                break;
            case R.id.cl_birth:
                showSilenceLoading(UserFragment.this);
                view.setClickable(false);
                userViewModel.updateUser(User.FIELD_BIRTH, random.nextLong());
                break;
            case R.id.cl_gender:
                showSilenceLoading(UserFragment.this);
                view.setClickable(false);
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
    public void onDestroy() {
        super.onDestroy();
        detachSilenceLoading(UserFragment.this);
    }

    @Override
    public void initView(View view) {
        Log.d(TAG, "initView: " + view.toString());
        tvHeight = view.findViewById(R.id.tv_height);
        tvWeight = view.findViewById(R.id.tv_weight);
        tvBirth = view.findViewById(R.id.tv_birth);
        tvGender = view.findViewById(R.id.tv_gender);
        clHeight = view.findViewById(R.id.cl_height);
        clWeight = view.findViewById(R.id.cl_weight);
        clBirth = view.findViewById(R.id.cl_birth);
        clGender = view.findViewById(R.id.cl_gender);
        skvSilenceLoading = view.findViewById(R.id.skv_nav_loading);
        attachSilenceLoading(UserFragment.this, skvSilenceLoading);
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
            hideSilenceLoading(UserFragment.this);
            clHeight.setClickable(true);
            clWeight.setClickable(true);
            clGender.setClickable(true);
            clBirth.setClickable(true);
            tvBirth.setText(mBaseApplication.sdf_yyyy_MM_dd_hh_mm_ss.format(new Date(user.getBirth())));
            tvGender.setText(user.getGender().getName());
            tvHeight.setText(String.format("%.02f", user.getHeight()));
            tvWeight.setText(String.format("%.02f", user.getWeight()));
        });
        showSilenceLoading(UserFragment.this);
        userViewModel.getUser();
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
