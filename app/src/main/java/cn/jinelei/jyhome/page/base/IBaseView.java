package cn.jinelei.jyhome.page.base;

import android.content.Context;

public interface IBaseView {
    void showLoading();

    void hideLoading();

    void showToast(String msg);

    void showError(Exception e);

    Context getContext();
}
