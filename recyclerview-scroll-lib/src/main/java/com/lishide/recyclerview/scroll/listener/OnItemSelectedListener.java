package com.lishide.recyclerview.scroll.listener;

import android.view.View;

/**
 * item 选定监听
 *
 * @author lishide
 * @date 2017/4/12
 */
public interface OnItemSelectedListener {
    /**
     * item 选定
     *
     * @param view     view
     * @param position position
     */
    void onItemSelected(View view, int position);
}
