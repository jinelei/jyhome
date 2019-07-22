package cn.jinelei.jyhome.page.base.mvp;


import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Optional;

public class BasePresenter<V extends IBaseView> {
    protected Optional<Reference<V>> optViewRef;

    public void attachView(V view) {
        optViewRef = Optional.of(new WeakReference<>(view));
    }

    public void detachView() {
        optViewRef.ifPresent(Reference::clear);
        optViewRef = Optional.empty();
    }

    public Optional<V> getView() {
        return Optional.ofNullable(optViewRef.map(Reference::get)
                .orElse(null));
    }

}
