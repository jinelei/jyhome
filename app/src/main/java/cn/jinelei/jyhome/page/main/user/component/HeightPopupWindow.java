package cn.jinelei.jyhome.page.main.user.component;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import cn.jinelei.jyhome.R;

public class HeightPopupWindow extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "GenderPopupWindow";
    private final View rootView;
    private Context mContext;

    public HeightPopupWindow(Context context) {
        super(context);
        Log.d(TAG, "GenderPopupWindow");
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(R.layout.popup_window_gender, null);
        rootView.findViewById(R.id.ll_btn_container).findViewById(R.id.btnConfirm).setOnClickListener(this);
        initView(rootView);
        initEvent();
        setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
    }

    private void initView(View view) {

    }

    private void initEvent() {

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: " + view.getId());
        switch (view.getId()) {
            case R.id.btnConfirm:

                break;
            default:
                break;
        }
    }
}
