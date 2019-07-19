package cn.jinelei.jyhome.page;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.page.base.BaseFragment;

public class TestFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initEvent() {

    }

}
