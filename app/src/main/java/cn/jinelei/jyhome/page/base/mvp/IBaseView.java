package cn.jinelei.jyhome.page.base.mvp;

import android.content.Context;

public interface IBaseView {
    void showToast(String msg);

    void showError(Exception e);

    Context getContext();
}
