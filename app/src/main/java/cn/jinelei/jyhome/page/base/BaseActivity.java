package cn.jinelei.jyhome.page.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import java.util.Optional;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.page.base.mvp.loading.ILoadingView;

public abstract class BaseActivity extends AppCompatActivity implements ILoadingView {
    private static final String TAG = "BaseActivity";
    private Optional<AlertDialog> optAlertDialog;

    public abstract void initView();

    public abstract void initEvent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        initLoading();
    }

    public void initLoading() {
        Log.d(TAG, "initLoading");
        optAlertDialog = Optional.of(
                new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.JyAlertDialog))
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
    public void showLoading() {
        Log.d(TAG, "showLoading");
        optAlertDialog.ifPresent(alertDialog -> {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            alertDialog.show();
        });
    }

    @Override
    public void hideLoading() {
        Log.d(TAG, "hideLoading");
        optAlertDialog.ifPresent(alertDialog -> {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            alertDialog.hide();
        });
    }

    @Override
    public void showToast(String msg) {
        Log.d(TAG, "showToast: " + msg);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(Exception e) {
        Log.d(TAG, "showError: " + e.getMessage());
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

}
