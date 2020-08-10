package com.lishide.recyclerview.scroll.listener;

import android.view.View;

/**
 * item 长按监听
 *
 * @author lishide
 * @date 2017/4/12
 */
public interface OnItemLongClickListener {
    /**
     * item 长按
     *
     * @param view     view
     * @param position position
     */
    void onItemLongClick(View view, int position);
}
