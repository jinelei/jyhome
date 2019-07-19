package cn.jinelei.jyhome.page.base;

public interface ICallback<T> {
    void onSuccess(T data);
    void onFailure(T data);
    void onError(RuntimeException e);
    void onComplete();
}
