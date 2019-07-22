package cn.jinelei.jyhome.page.home;

import cn.jinelei.jyhome.page.base.mvp.IBaseView;
import cn.jinelei.jyhome.page.base.mvp.loading.ILoadingView;

public interface IMainView extends IBaseView, ILoadingView {
    void showData(String param);
}
