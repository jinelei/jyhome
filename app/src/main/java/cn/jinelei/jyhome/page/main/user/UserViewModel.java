package cn.jinelei.jyhome.page.main.user;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.base.Strings;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import cn.jinelei.jyhome.model.User;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";
    private final Handler handler = new Handler();
    private final User user = new User();
    public MutableLiveData<User> userData = new MutableLiveData<>();

    public void getUser() {
        Log.d(TAG, "getUser: postDelayed 3000");
        handler.postDelayed(() -> {
            Random random = new Random();
            user.setAvator("avator_" + random.nextInt(10));
            user.setNickname("nickname_" + random.nextInt(10));
            user.setPhoneNumber("13111112222_" + random.nextInt(10));
            user.setGender(User.Gender.FEMALE);
            Log.d(TAG, "getUser: " + user.toString());
            userData.postValue(user);
        }, 3000);
    }

    public void updateUser(String field, @NotNull Object value) {
        Log.d(TAG, "updateUser: " + field + " -> " + value);
        boolean needRefresh = true;
        switch (field) {
            case User.FIELD_NICKNAME:
                if (!Strings.isNullOrEmpty(value.toString())) {
                    user.setNickname(value.toString());
                } else {
                    Log.e(TAG, "updateUser: invalid nickname: " + value.toString());
                }
                break;
            case User.FIELD_AVATOR:
                if (!Strings.isNullOrEmpty(value.toString())) {
                    user.setAvator(value.toString());
                } else {
                    Log.e(TAG, "updateUser: invalid avator: " + value.toString());
                }
                break;
            case User.FIELD_PHONENUMBER:
                if (!Strings.isNullOrEmpty(value.toString())) {
                    user.setPhoneNumber(value.toString());
                } else {
                    Log.e(TAG, "updateUser: invalid phone number: " + value.toString());

                }
                break;
            case User.FIELD_GENDER:
                User.Gender gender = User.Gender.forObject(value);
                if (gender != null) {
                    user.setGender(gender);
                } else {
                    Log.e(TAG, "updateUser: invalid gender: " + value.toString());
                }
                break;
            case User.FIELD_HEIGHT:
                try {
                    double height = Double.parseDouble(value.toString());
                    user.setHeight(height);
                } catch (Exception e) {
                    Log.e(TAG, "updateUser: invalid height: " + value.toString());
                    e.printStackTrace();
                    break;
                }
                break;
            case User.FIELD_WEIGHT:
                try {
                    double weight = Double.parseDouble(value.toString());
                    user.setWeight(weight);
                } catch (Exception e) {
                    Log.e(TAG, "updateUser: invalid weight: " + value.toString());
                    e.printStackTrace();
                    break;
                }
                break;
            case User.FIELD_BIRTH:
                try {
                    long birth = Long.parseLong(value.toString());
                    user.setBirth(birth);
                } catch (Exception e) {
                    Log.e(TAG, "updateUser: invalid birth: " + value.toString());
                    e.printStackTrace();
                    break;
                }
                break;
            default:
                needRefresh = false;
        }
        if (needRefresh) {
            userData.postValue(user);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
