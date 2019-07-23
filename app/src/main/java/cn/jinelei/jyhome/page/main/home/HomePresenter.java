package cn.jinelei.jyhome.page.main.home;

import android.util.Log;

import cn.jinelei.jyhome.model.FakeNetRequestModel;
import cn.jinelei.jyhome.page.base.mvp.BasePresenter;
import cn.jinelei.jyhome.page.base.mvp.ICallback;

public class HomePresenter extends BasePresenter<IMainView> {
    private static final String TAG = "HomePresenter";

    public void getData(String param) {
        Log.d(TAG, "getData: " + param);
        getView().ifPresent(iMainView -> {
            iMainView.showLoading();
            FakeNetRequestModel.getNetData(param, new ICallback<String>() {
                @Override
                public void onSuccess(String data) {
                    getView().ifPresent(iMainView -> {
                        iMainView.showData(data);
                        iMainView.hideLoading();
                    });
                }

                @Override
                public void onFailure(String data) {
                    getView().ifPresent(iMainView -> {
                        iMainView.showToast(data);
                        iMainView.hideLoading();
                    });
                }

                @Override
                public void onError(RuntimeException e) {
                    getView().ifPresent(iMainView -> {
                        iMainView.showError(e);
                        iMainView.hideLoading();
                    });
                }

                @Override
                public void onComplete() {
                    getView().ifPresent(iMainView -> {
                        iMainView.hideLoading();
                    });
                }
            });
        });

    }
}
