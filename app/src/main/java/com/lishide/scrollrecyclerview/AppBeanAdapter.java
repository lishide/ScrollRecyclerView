package com.lishide.scrollrecyclerview;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lishide.recyclerview.scroll.listener.OnItemClickListener;
import com.lishide.recyclerview.scroll.listener.OnItemKeyListener;
import com.lishide.recyclerview.scroll.listener.OnItemLongClickListener;
import com.lishide.recyclerview.scroll.listener.OnItemSelectedListener;

import java.util.List;

public class AppBeanAdapter extends RecyclerView.Adapter<AppBeanAdapter.ViewHolder> {
    private static final String TAG = AppBeanAdapter.class.getSimpleName();
    private Context mContext;
    private List<AppBean> mList;
    private LayoutInflater inflater;
    private int currentPosition;
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemKeyListener mOnItemKeyListener;

    public AppBeanAdapter(Context context, List<AppBean> list) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_grid_apps, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mImageView.setImageDrawable(mList.get(position).getAppIcon());
        holder.mTextView.setText(mList.get(position).getAppName());

        // 设置 itemView 可以获得焦点
        holder.itemView.setFocusable(true);
        holder.itemView.setTag(position);
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "焦点监听器被调用了");
                Log.d(TAG, "hasFocus=" + hasFocus);
                if (hasFocus) {
                    currentPosition = (int) holder.itemView.getTag();
                    Log.d(TAG, "getTag=" + currentPosition);
                    Log.i(TAG, "The view hasFocus=" + v + ", holder.itemView=" + holder.itemView);
                    mOnItemSelectedListener.onItemSelected(v, currentPosition);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, currentPosition);
            }
        });

        holder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                mOnItemKeyListener.onItemKey(v, keyCode, event, currentPosition);
                return false;
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemLongClickListener.onItemLongClick(v, currentPosition);
                return true;
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_grid_item_icon);
            mTextView = (TextView) itemView.findViewById(R.id.tv_grid_item_name);
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemKeyListener(OnItemKeyListener onItemKeyListener) {
        this.mOnItemKeyListener = onItemKeyListener;
    }

}
