package cn.jinelei.jyhome.page.user;

import android.os.Bundle;

import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.page.base.BaseFragment;

public class UserFragment extends BaseFragment {
    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    protected void initAllMembersView(Bundle saveInstanceState) {

    }

    public enum Singleton implements JySingleton<UserFragment> {
        INSTANCE {
            public UserFragment instance = new UserFragment();

            @Override
            public UserFragment getInstance() {
                return instance;
            }
        }
    }
}
