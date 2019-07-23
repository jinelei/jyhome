package cn.jinelei.jyhome.page.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.jinelei.jyhome.exception.ActivityNotAttachedException;
import cn.jinelei.jyhome.page.base.mvp.loading.ILoadingView;


public abstract class BaseFragment extends Fragment implements ILoadingView {
    private static final String TAG = "BaseFragment";

    public abstract void initView(View view);

    public abstract void initEvent();

    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        this.mContext = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showLoading() {
        Log.d(TAG, "showLoading");
        checkActivityAttached();
        ((BaseActivity) mContext).showLoading();
    }

    @Override
    public void hideLoading() {
        Log.d(TAG, "hideLoading");
        checkActivityAttached();
        ((BaseActivity) mContext).hideLoading();
    }

    @Override
    public void showToast(String msg) {
        Log.d(TAG, "showToast: " + msg);
        checkActivityAttached();
        ((BaseActivity) mContext).showToast(msg);
    }

    @Override
    public void showError(Exception e) {
        Log.d(TAG, "showError: " + e.getMessage());
        checkActivityAttached();
        ((BaseActivity) mContext).showError(e);
    }

    public void checkActivityAttached() {
        Log.d(TAG, "checkActivityAttached: " + (null == getActivity()));
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }
}
