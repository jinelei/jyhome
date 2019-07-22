package cn.jinelei.jyhome.page.test;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.page.base.BaseActivity;

public class TestActivity extends BaseActivity implements ITestView {
    private final TestPresenter presenter = new TestPresenter();
    private TextView tvMessage;
    private View.OnClickListener clickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_success:
                presenter.getData("success");
                break;
            case R.id.btn_failure:
                presenter.getData("failure");
                break;
            case R.id.btn_error:
                presenter.getData("error");
                break;
            case R.id.btn_complete:
                presenter.getData("complete");
                break;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        presenter.attachView(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public void initView() {
        findViewById(R.id.btn_success).setOnClickListener(clickListener);
        findViewById(R.id.btn_failure).setOnClickListener(clickListener);
        findViewById(R.id.btn_error).setOnClickListener(clickListener);
        findViewById(R.id.btn_complete).setOnClickListener(clickListener);
        tvMessage = findViewById(R.id.tv_message);

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void showError(Exception e) {
        tvMessage.append(String.format("show error: %s\n", e.getMessage()));
    }

    @Override
    public void showData(String data) {
        tvMessage.append(String.format("show data: %s\n", data));
    }

}
