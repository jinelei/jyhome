package cn.jinelei.jyhome.page.base;


public class BasePresenter<V extends IBaseView> {
    private V view;

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }

    public boolean isViewAttached() {
        return this.view != null;
    }

    public V getView() {
        return this.view;
    }

}
