package cn.jinelei.jyhome.page.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Optional;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.BaseApplication;
import cn.jinelei.jyhome.page.base.feature.ILoadingDialog;
import cn.jinelei.jyhome.page.base.feature.ISilenceLoading;
import cn.jinelei.jyhome.page.base.feature.IToastFeature;

public abstract class BaseActivity extends AppCompatActivity implements ILoadingDialog, IToastFeature, ISilenceLoading<BaseActivity> {
    private static final String TAG = "BaseActivity";
    private Optional<AlertDialog> optAlertDialog;

    protected BaseApplication mBaseApplication;

    public abstract void initView();

    public abstract void initEvent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        this.mBaseApplication = BaseApplication.Singleton.INSTANCE.getInstance();
        Optional.ofNullable(getSupportActionBar()).ifPresent(actionBar -> actionBar.hide());
        initLoading();
    }

    public void initLoading() {
        Log.d(TAG, "initLoading");
        optAlertDialog = Optional.of(
                new AlertDialog.Builder(new ContextThemeWrapper(BaseActivity.this, R.style.JyAlertDialog))
                        .setCancelable(false)
                        .setView(R.layout.dialog_loading)
                        .create()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void showLoadingDialog() {
        Log.d(TAG, "showLoadingDialog");
        optAlertDialog.ifPresent(alertDialog -> {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            alertDialog.show();
        });
    }

    @Override
    public void hideLoadingDialog() {
        Log.d(TAG, "hideLoadingDialog");
        optAlertDialog.ifPresent(alertDialog -> {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            alertDialog.hide();
        });
    }

    @Override
    public void attachSilenceLoading(BaseActivity activity, SpinKitView view) {
        Log.d(TAG, "attachSilenceLoading: " + activity.toString());
        if (!mBaseApplication.mActivitySilenceLoadingMap.containsKey(activity)) {
            mBaseApplication.mActivitySilenceLoadingMap.put(activity, view);
        }

    }

    @Override
    public void detachSilenceLoading(BaseActivity activity) {
        Log.d(TAG, "detachSilenceLoading: " + activity.toString());
        if (mBaseApplication.mActivitySilenceLoadingMap.containsKey(activity)) {
            mBaseApplication.mActivitySilenceLoadingMap.remove(activity);
        }

    }

    @Override
    public boolean isAttachedSilenceLoading(BaseActivity activity) {
        Log.d(TAG, "isAttachedSilenceLoading: " + activity.toString());
        return mBaseApplication.mActivitySilenceLoadingMap.containsKey(activity)
                && mBaseApplication.mActivitySilenceLoadingMap.get(activity) != null;
    }

    @Override
    public void showSilenceLoading(BaseActivity activity) {
        Log.d(TAG, "showSilenceLoading: " + activity.toString());
        if (isAttachedSilenceLoading(activity)) {
            Object o = mBaseApplication.mActivitySilenceLoadingMap.get(activity);
            if (o != null && o instanceof SpinKitView) {
                ((SpinKitView) o).setVisibility(View.VISIBLE);
                ((SpinKitView) o).setIndeterminate(true);
            }
        }
    }

    @Override
    public void hideSilenceLoading(BaseActivity activity) {
        Log.d(TAG, "hideSilenceLoading: " + activity.toString());
        if (isAttachedSilenceLoading(activity)) {
            Object o = mBaseApplication.mActivitySilenceLoadingMap.get(activity);
            if (o != null && o instanceof SpinKitView) {
                ((SpinKitView) o).setVisibility(View.GONE);
                ((SpinKitView) o).setIndeterminate(false);
            }
        }
    }

    @Override
    public void showToast(String msg) {
        Log.d(TAG, "showToast: " + msg);
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
