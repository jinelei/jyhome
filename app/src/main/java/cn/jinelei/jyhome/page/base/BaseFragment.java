package cn.jinelei.jyhome.page.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.jinelei.jyhome.exception.ActivityNotAttachedException;
import cn.jinelei.jyhome.page.base.mvp.loading.ILoadingView;


public abstract class BaseFragment extends Fragment implements ILoadingView {

    public abstract void initView(View view);

    public abstract void initEvent();

    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mContext = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showLoading() {
        checkActivityAttached();
        ((BaseActivity) mContext).showLoading();
    }

    @Override
    public void hideLoading() {
        checkActivityAttached();
        ((BaseActivity) mContext).hideLoading();
    }

    @Override
    public void showToast(String msg) {
        checkActivityAttached();
        ((BaseActivity) mContext).showToast(msg);
    }

    @Override
    public void showError(Exception e) {
        checkActivityAttached();
        ((BaseActivity) mContext).showError(e);
    }

    protected boolean isAttachedContext() {
        return getActivity() != null;
    }

    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }
}
