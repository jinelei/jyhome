package cn.jinelei.jyhome.model;

import android.os.Handler;
import android.util.Log;

import cn.jinelei.jyhome.page.base.mvp.ICallback;

public class FakeNetRequestModel {
    private static final String TAG = "FakeNetRequestModel";

    public static void getNetData(final String param, final ICallback<String> callback) {
        Log.d(TAG, "getNetData: " + param);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            switch (param) {
                case "success":
                    callback.onSuccess("on success");
                    break;
                case "failure":
                    callback.onFailure("on failure");
                    break;
                case "error":
                    callback.onError(new RuntimeException("on error"));
                    break;
                case "complete":
                    callback.onComplete();
                    break;
            }
        }, 2000);
    }
}
