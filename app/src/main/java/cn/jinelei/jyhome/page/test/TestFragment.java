package cn.jinelei.jyhome.page.test;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.page.base.BaseFragment;

public class TestFragment extends BaseFragment implements ITestView {
    private TestPresenter testPresenter = new TestPresenter();

    private final View.OnClickListener clickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_success:
                testPresenter.getData("success");
                break;
            case R.id.btn_failure:
                testPresenter.getData("failure");
                break;
            case R.id.btn_complete:
                testPresenter.getData("complete");
                break;
            case R.id.btn_error:
                testPresenter.getData("error");
                break;
        }
    };
    private TextView tvMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_test, container, false);
        initView(rootView);
        initEvent();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        testPresenter.detachView();
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.btn_success).setOnClickListener(clickListener);
        view.findViewById(R.id.btn_failure).setOnClickListener(clickListener);
        view.findViewById(R.id.btn_error).setOnClickListener(clickListener);
        view.findViewById(R.id.btn_complete).setOnClickListener(clickListener);
        tvMessage = view.findViewById(R.id.tv_message);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void showData(String data) {
        tvMessage.append(String.format("show data: %s\n", data));
    }
}
