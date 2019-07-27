package cn.jinelei.jyhome.page.base.feature;

import android.content.Context;

import com.github.ybq.android.spinkit.SpinKitView;

public interface ISilenceLoading {

    void attachSilenceLoading(Context context, SpinKitView view);

    void detachSilenceLoading(Context context);

    boolean isAttachedSilenceLoading(Context context);

    void showSilenceLoading(Context context);

    void hideSilenceLoading(Context context);

}
