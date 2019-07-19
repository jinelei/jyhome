package cn.jinelei.jyhome.page.home;

import cn.jinelei.jyhome.page.base.BasePresenter;
import cn.jinelei.jyhome.model.FakeNetRequestModel;
import cn.jinelei.jyhome.page.base.ICallback;

public class HomePresenter extends BasePresenter<IMainView> {
    public void getData(String param) {
        if (!isViewAttached()) {
            return;
        }
        getView().showLoading();
        FakeNetRequestModel.getNetData(param, new ICallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (isViewAttached()) {
                    getView().showData(data);
                    getView().hideLoading();
                }
            }

            @Override
            public void onFailure(String data) {
                if (isViewAttached()) {
                    getView().showToast(data);
                    getView().hideLoading();
                }
            }

            @Override
            public void onError(RuntimeException e) {
                if (isViewAttached()) {
                    getView().showError(e);
                    getView().hideLoading();
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    getView().hideLoading();
                }
            }
        });
    }
}
