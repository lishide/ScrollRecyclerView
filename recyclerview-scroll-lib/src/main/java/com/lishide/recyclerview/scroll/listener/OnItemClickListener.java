package com.lishide.recyclerview.scroll.listener;

import android.view.View;

/**
 * item 点击监听
 *
 * @author lishide
 * @date 2017/4/12
 */
public interface OnItemClickListener {
    /**
     * item 点击
     *
     * @param view     view
     * @param position position
     */
    void onItemClick(View view, int position);
}
