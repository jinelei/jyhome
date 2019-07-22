package cn.jinelei.jyhome.page.base.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T getChildView(int id) {
        return (T) itemView.findViewById(id);
    }
}

