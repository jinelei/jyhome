package cn.jinelei.jyhome.page.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected abstract void onBindData(BaseViewHolder holder, T data, int position);

    protected Context mContext;
    protected final List<T> mDataSet = new ArrayList<>();
    protected LayoutInflater mInflater;
    protected int layoutRes;

    public BaseRecyclerViewAdapter(Context context, List<T> data, @LayoutRes int layoutRes) {
        mDataSet.clear();
        mDataSet.addAll(data);
        this.mContext = context;
        this.layoutRes = layoutRes;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void refresh(List<T> data) {
        try {
            mDataSet.clear();
            mDataSet.addAll(data);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(mInflater.inflate(layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        onBindData(holder, mDataSet.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
