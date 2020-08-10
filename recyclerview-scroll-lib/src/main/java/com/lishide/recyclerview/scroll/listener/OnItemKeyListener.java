package com.lishide.recyclerview.scroll.listener;

import android.view.KeyEvent;
import android.view.View;

/**
 * 遥控器其他按键监听
 *
 * @author lishide
 * @date 2017/4/12
 */
public interface OnItemKeyListener {
    /**
     * 遥控器其他按键
     *
     * @param view     view
     * @param keyCode  keyCode
     * @param event    event
     * @param position position
     */
    void onItemKey(View view, int keyCode, KeyEvent event, int position);
}
