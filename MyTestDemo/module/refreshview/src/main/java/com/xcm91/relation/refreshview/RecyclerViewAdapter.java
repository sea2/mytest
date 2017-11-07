package com.xcm91.relation.refreshview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final int TYPE_ITEM = 0;//正常布局
    private final int TYPE_FOOTER = 1;//加载布局
    private Context context;
    private List data;
    private boolean loadEnable = true;
    private boolean loadComplete = false;

    public RecyclerViewAdapter(Context context, List data) {
        this.context = context;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (data == null || data.isEmpty() || data.size() == 0)
            return 0;
        else {
            return data.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ((position + 1) == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_base, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder != null && holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
            mItemViewHolder.tv.setText(String.valueOf(position));

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            } else {
                holder.itemView.setOnClickListener(null);
                holder.itemView.setOnLongClickListener(null);
            }
        } else if (holder != null && holder instanceof FootViewHolder) {//加载item的布局
            FootViewHolder mFootViewHolder = (FootViewHolder) holder;
            if (!loadEnable) {
                mFootViewHolder.tv_load_remark.setVisibility(View.GONE);
                mFootViewHolder.progressBar.setVisibility(View.GONE);
            } else {
                mFootViewHolder.tv_load_remark.setText("加载中...");
                mFootViewHolder.tv_load_remark.setVisibility(View.VISIBLE);
                mFootViewHolder.progressBar.setVisibility(View.VISIBLE);
            }
    }
}


private class ItemViewHolder extends ViewHolder {
    TextView tv;

    ItemViewHolder(View view) {
        super(view);
        tv = (TextView) view.findViewById(R.id.tv_date);
    }
}

private class FootViewHolder extends ViewHolder {
    TextView tv_load_remark;
    ProgressBar progressBar;

    FootViewHolder(View view) {
        super(view);
        tv_load_remark = (TextView) view.findViewById(R.id.tv_load_remark);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

}


    public void setLoadEnable(boolean loadEnable) {
        this.loadEnable = loadEnable;
    }

    public boolean isLoadEnable() {
        return loadEnable;
    }
}