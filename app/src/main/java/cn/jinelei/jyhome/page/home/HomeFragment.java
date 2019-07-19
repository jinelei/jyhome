package cn.jinelei.jyhome.page.home;

import android.os.Bundle;

import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.page.base.BaseFragment;

public class HomeFragment extends BaseFragment {
    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    protected void initAllMembersView(Bundle saveInstanceState) {

    }

    public enum Singleton implements JySingleton<HomeFragment> {
        INSTANCE {
            public HomeFragment instance = new HomeFragment();

            @Override
            public HomeFragment getInstance() {
                return instance;
            }
        }
    }
}
