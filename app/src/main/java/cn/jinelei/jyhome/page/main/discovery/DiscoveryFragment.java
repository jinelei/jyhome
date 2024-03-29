package cn.jinelei.jyhome.page.main.discovery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.common.base.Strings;

import java.util.ArrayList;

import cn.jinelei.jyhome.R;
import cn.jinelei.jyhome.base.JySingleton;
import cn.jinelei.jyhome.page.base.BaseFragment;
import cn.jinelei.jyhome.page.base.adapter.BaseRecyclerViewAdapter;
import cn.jinelei.jyhome.page.base.adapter.BaseViewHolder;

public class DiscoveryFragment extends BaseFragment {
    private static final String TAG = "DiscoveryFragment";
    private RecyclerView recyclerView;
    private TextView tvTitle;
    private ImageView ivNavLeft;
    private ImageView ivNavRight;
    private SpinKitView skvNavLoading;
    private final ArrayList<DiscoveryCardItem> allDiscoveryCardItems = new ArrayList<>();
    private View.OnClickListener itemClickListener = view -> {

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_discovery, container, false);
        initView(rootView);
        initEvent();
        return rootView;
    }

    @Override
    public void initView(View view) {
        Log.d(TAG, "initView: " + view.toString());
        View navBar = view.findViewById(R.id.nav_bar);
        tvTitle = navBar.findViewById(R.id.tv_nav_title);
        ivNavLeft = navBar.findViewById(R.id.iv_nav_left);
        ivNavRight = navBar.findViewById(R.id.iv_nav_right);
        skvNavLoading = navBar.findViewById(R.id.skv_nav_loading);
        tvTitle.setText(R.string.navigation_discovery);
        recyclerView = view.findViewById(R.id.rv_discovery);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initEvent() {
        Log.d(TAG, "initEvent");
        for (int i = 0; i < 10; i++) {
            allDiscoveryCardItems.add(new DiscoveryCardItem(R.drawable.ic_launcher_background, R.string.app_name, itemClickListener));
        }
        BaseRecyclerViewAdapter<DiscoveryCardItem> adapter = new BaseRecyclerViewAdapter<DiscoveryCardItem>(getActivity(), allDiscoveryCardItems, R.layout.item_discovery) {
            @Override
            protected void onBindData(BaseViewHolder holder, DiscoveryCardItem item, int position) {
                LinearLayout llContainer = holder.getChildView(R.id.item_discovery_container);
                TextView tvTitle = holder.getChildView(R.id.item_discovery_title);
                llContainer.setOnClickListener(item.listener);
                if (!Strings.isNullOrEmpty(item.title)) {
                    tvTitle.setText(item.title);
                } else {
                    tvTitle.setText(getActivity().getString(item.titleRes));
                }
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public static class DiscoveryCardItem {
        private int backgroundRes;
        private int titleRes;
        private String title;
        private View.OnClickListener listener;

        public DiscoveryCardItem(int backgroundRes, int titleRes, View.OnClickListener listener) {
            this.backgroundRes = backgroundRes;
            this.titleRes = titleRes;
            this.listener = listener;
        }

        public DiscoveryCardItem(int backgroundRes, String title, View.OnClickListener listener) {
            this.backgroundRes = backgroundRes;
            this.title = title;
            this.listener = listener;
        }
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
