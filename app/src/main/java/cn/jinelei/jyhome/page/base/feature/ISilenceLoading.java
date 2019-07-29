package cn.jinelei.jyhome.page.base.feature;

import com.github.ybq.android.spinkit.SpinKitView;

public interface ISilenceLoading<T> {

    void attachSilenceLoading(T t, SpinKitView view);

    void detachSilenceLoading(T t);

    boolean isAttachedSilenceLoading(T t);

    void showSilenceLoading(T t);

    void hideSilenceLoading(T t);

}
