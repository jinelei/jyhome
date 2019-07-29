package cn.jinelei.jyhome.page.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.ybq.android.spinkit.SpinKitView;

import cn.jinelei.jyhome.base.BaseApplication;
import cn.jinelei.jyhome.exception.ActivityNotAttachedException;
import cn.jinelei.jyhome.page.base.feature.ILoadingDialog;
import cn.jinelei.jyhome.page.base.feature.ISilenceLoading;
import cn.jinelei.jyhome.page.base.feature.IToastFeature;


public abstract class BaseFragment extends Fragment implements ILoadingDialog, IToastFeature, ISilenceLoading<BaseFragment> {
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

    @Override
    public void attachSilenceLoading(BaseFragment fragment, SpinKitView view) {
        if (!mBaseApplication.mFragmentSilenceLoadingMap.containsKey(fragment)) {
            mBaseApplication.mFragmentSilenceLoadingMap.put(fragment, view);
        }
    }

    @Override
    public void detachSilenceLoading(BaseFragment fragment) {
        if (mBaseApplication.mFragmentSilenceLoadingMap.containsKey(fragment)) {
            mBaseApplication.mFragmentSilenceLoadingMap.remove(fragment);
        }
    }

    @Override
    public boolean isAttachedSilenceLoading(BaseFragment fragment) {
        return mBaseApplication.mFragmentSilenceLoadingMap.containsKey(fragment) && mBaseApplication.mFragmentSilenceLoadingMap.get(fragment) != null;
    }

    @Override
    public void showSilenceLoading(BaseFragment fragment) {
        if (isAttachedSilenceLoading(fragment)) {
            Object o = mBaseApplication.mFragmentSilenceLoadingMap.get(fragment);
            if (o != null && o instanceof SpinKitView) {
                ((SpinKitView) o).setVisibility(View.VISIBLE);
                ((SpinKitView) o).setIndeterminate(true);
            }
        }
    }

    @Override
    public void hideSilenceLoading(BaseFragment fragment) {
        if (isAttachedSilenceLoading(fragment)) {
            Object o = mBaseApplication.mFragmentSilenceLoadingMap.get(fragment);
            if (o != null && o instanceof SpinKitView) {
                ((SpinKitView) o).setVisibility(View.GONE);
                ((SpinKitView) o).setIndeterminate(false);
            }
        }
    }
}
