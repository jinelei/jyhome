package cn.jinelei.jyhome.page.main.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.BaseApplication;
import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.model.User;
import cn.jinelei.jyhome.page.base.BaseFragment;
import cn.jinelei.jyhome.page.base.feature.ICallback;
import cn.jinelei.jyhome.page.main.user.component.BirthPopupWindow;

public class UserFragment extends BaseFragment {
    private static final String TAG = "UserFragment";
    private final Random random = new Random();
    private View rootView;
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
    private BirthPopupWindow mBirthPopupWindow;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    private PopupWindow.OnDismissListener mOnDismissListener = () -> {
        setBackgroundAlpha(1.0f);
        mBirthPopupWindow.resetView();
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
//                showSilenceLoading(UserFragment.this);
//                view.setClickable(false);
//                userViewModel.updateUser(User.FIELD_BIRTH, random.nextLong());
                setBackgroundAlpha(0.7f);
                mBirthPopupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
        rootView = view;
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

        mBirthPopupWindow = new BirthPopupWindow(this.mContext, new ICallback<Calendar>() {
            @Override
            public void onSuccess(Calendar data) {
                mBirthPopupWindow.dismiss();
                tvBirth.setText(BaseApplication.sdf_yyyy_MM_dd.format(data.getTime()));
            }

            @Override
            public void onFailure(Calendar data) {

            }

            @Override
            public void onError(RuntimeException e) {

            }

            @Override
            public void onComplete() {

            }
        });
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
            tvBirth.setText(BaseApplication.sdf_yyyy_MM_dd.format(new Date(user.getBirth())));
            tvGender.setText(user.getGender().getName());
            tvHeight.setText(String.format("%.02f", user.getHeight()));
            tvWeight.setText(String.format("%.02f", user.getWeight()));
        });
        showSilenceLoading(UserFragment.this);
        userViewModel.getUser();
        mBirthPopupWindow.setOnDismissListener(mOnDismissListener);
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
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
