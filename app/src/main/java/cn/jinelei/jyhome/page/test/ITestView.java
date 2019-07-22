package cn.jinelei.jyhome.page.test;

import cn.jinelei.jyhome.page.base.mvp.loading.ILoadingView;

public interface ITestView extends ILoadingView {
    void showData(String param);
}
