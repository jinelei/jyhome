package cn.jinelei.jyhome.page.discovery;

import android.os.Bundle;

import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.page.base.BaseFragment;

public class DiscoveryFragment extends BaseFragment {
    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    protected void initAllMembersView(Bundle saveInstanceState) {

    }

    public enum Singleton implements JySingleton<DiscoveryFragment> {
        INSTANCE {
            public DiscoveryFragment instance = new DiscoveryFragment();

            @Override
            public DiscoveryFragment getInstance() {
                return instance;
            }
        }
    }
}
