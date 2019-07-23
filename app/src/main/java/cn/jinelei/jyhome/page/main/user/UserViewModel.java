package cn.jinelei.jyhome.page.main.user;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Optional;

import cn.jinelei.jyhome.model.User;

public class UserViewModel extends ViewModel {
    public MutableLiveData<User> userData;
    private User user = new User();
    private Optional<User> optUser = Optional.empty();

    public void getUser() {
        new Handler().postDelayed(() -> userData.postValue(optUser.orElse(new User())), 3000);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        optUser = Optional.empty();
    }
}
