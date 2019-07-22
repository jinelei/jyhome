package cn.jinelei.jyhome.page;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.page.base.BaseFragment;
import cn.jinelei.jyhome.page.test.TestActivity;

public class TestFragment extends BaseFragment {

    private final View.OnClickListener clickListener = view -> {
        Intent intent = new Intent();
        intent.setClass(getActivity(), TestActivity.class);
        startActivity(intent);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        initView(rootView);
        return rootView;
    }


    @Override
    public void initView(View view) {
        view.findViewById(R.id.tv_test).setOnClickListener(clickListener);
    }

    @Override
    public void initEvent() {

    }

}
