package cn.jinelei.jyhome.page.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import java.util.Optional;

import cn.jinelei.jyhome.R;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private Optional<AlertDialog> optAlertDialog;

    public abstract void initView();

    public abstract void initEvent();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initLoading();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoading();
    }

    public void initLoading() {
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
    }

    @Override
    public void showLoading() {
        optAlertDialog.ifPresent(alertDialog -> {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            alertDialog.show();
        });
    }

    @Override
    public void hideLoading() {
        optAlertDialog.ifPresent(alertDialog -> {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            alertDialog.hide();
        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

}
