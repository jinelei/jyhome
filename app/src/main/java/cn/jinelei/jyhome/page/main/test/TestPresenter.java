package cn.jinelei.jyhome.page.main.test;

import android.util.Log;

import cn.jinelei.jyhome.model.FakeNetRequestModel;
import cn.jinelei.jyhome.page.base.mvp.BasePresenter;
import cn.jinelei.jyhome.page.base.mvp.ICallback;

public class TestPresenter extends BasePresenter<ITestView> {
    private static final String TAG = "TestPresenter";

    public void getData(String param) {
        Log.d(TAG, "getData: " + param);
        getView().ifPresent(iTestView -> {
            iTestView.showLoading();
            FakeNetRequestModel.getNetData(param, new ICallback<String>() {
                @Override
                public void onSuccess(String data) {
                    getView().ifPresent(iTestView -> {
                        iTestView.showData(data);
                        iTestView.hideLoading();
                    });
                }

                @Override
                public void onFailure(String data) {
                    getView().ifPresent(iTestView -> {
                        iTestView.showToast(data);
                        iTestView.hideLoading();
                    });
                }

                @Override
                public void onError(RuntimeException e) {
                    getView().ifPresent(iTestView -> {
                        iTestView.showError(e);
                        iTestView.hideLoading();
                    });
                }

                @Override
                public void onComplete() {
                    getView().ifPresent(iTestView -> {
                        iTestView.hideLoading();
                    });
                }
            });
        });

    }
}
