package cn.jinelei.jyhome.page.main.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.BaseApplication;
import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.page.base.BaseFragment;

public class UserFragment extends BaseFragment {
    private static final String TAG = "UserFragment";
    private TextView tvTest;

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
    }

    @Override
    public void initEvent() {
        Log.d(TAG, "initEvent");
        UserViewModel userViewModel = new ViewModelProvider.AndroidViewModelFactory(BaseApplication.Singleton.INSTANCE.getInstance()).create(UserViewModel.class);
        userViewModel.userData.observe(this, user -> {
            tvTest.setText(user.toString());
        });
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
