package cn.jinelei.jyhome.page.main.user;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

import cn.jinelei.jyhome.model.User;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";
    private final Handler handler = new Handler();
    public MutableLiveData<User> userData = new MutableLiveData<>();

    public void getUser() {
        Log.d(TAG, "getUser: postDelayed 3000");
        handler.postDelayed(() -> {
            User user = new User();
            Random random = new Random();
            user.setAvator("avator_" + random.nextInt(10));
            user.setNickname("nickname_" + random.nextInt(10));
            user.setPhoneNumber("13111112222_" + random.nextInt(10));
            user.setGender(User.Gender.FEMALE);
            Log.d(TAG, "getUser: " + user.toString());
            userData.postValue(user);
        }, 3000);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
