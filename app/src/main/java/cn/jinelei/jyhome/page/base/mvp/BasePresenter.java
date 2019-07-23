package cn.jinelei.jyhome.page.base.mvp;


import android.util.Log;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Optional;

public class BasePresenter<V extends IBaseView> {
    private static final String TAG = "BasePresenter";
    protected Optional<Reference<V>> optViewRef;

    public void attachView(V view) {
        Log.d(TAG, "attachView: " + view.toString());
        optViewRef = Optional.of(new WeakReference<>(view));
    }

    public void detachView() {
        Log.d(TAG, "detachView: " + optViewRef.map(ref -> ref.get().toString()).orElse("unknown view"));
        optViewRef.ifPresent(Reference::clear);
        optViewRef = Optional.empty();
    }

    public Optional<V> getView() {
        Log.d(TAG, "getView: " + optViewRef.map(ref -> ref.get().toString()).orElse("unknown view"));
        return Optional.ofNullable(optViewRef.map(Reference::get)
                .orElse(null));
    }

}
