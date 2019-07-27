package cn.jinelei.jyhome.page.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.jinelei.jyhome.base.BaseApplication;
import cn.jinelei.jyhome.exception.ActivityNotAttachedException;
import cn.jinelei.jyhome.page.base.feature.ILoadingDialog;
import cn.jinelei.jyhome.page.base.feature.IToastFeature;


public abstract class BaseFragment extends Fragment implements ILoadingDialog, IToastFeature {
    private static final String TAG = "BaseFragment";

    public abstract void initView(View view);

    public abstract void initEvent();

    protected Context mContext;
    protected BaseApplication mBaseApplication;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        this.mContext = getActivity();
        this.mBaseApplication = BaseApplication.Singleton.INSTANCE.getInstance();
    }

    @Override
    public void showLoadingDialog() {
        Log.d(TAG, "showLoadingDialog");
        checkActivityAttached();
        ((BaseActivity) mContext).showLoadingDialog();
    }

    @Override
    public void hideLoadingDialog() {
        Log.d(TAG, "hideLoadingDialog");
        checkActivityAttached();
        ((BaseActivity) mContext).hideLoadingDialog();
    }

    @Override
    public void showToast(String msg) {
        Log.d(TAG, "showToast: " + msg);
        checkActivityAttached();
        ((BaseActivity) mContext).showToast(msg);
    }

    public void checkActivityAttached() {
        Log.d(TAG, "checkActivityAttached: " + (null == getActivity()));
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }
}
