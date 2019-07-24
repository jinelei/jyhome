package cn.jinelei.jyhome.page.main.home.test;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class TestViewModel extends ViewModel {
    private static final String TAG = "TestViewModel";
    private final Handler handler = new Handler();
    public MutableLiveData<String> testData = new MutableLiveData<>();

    public void getTestData(String message) {
        Log.d(TAG, "getTestData: postDelayed: 3000");
        handler.postDelayed(() -> {
            Random random = new Random();
            testData.postValue(message + "_" + random.nextInt());
        }, 3000);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
