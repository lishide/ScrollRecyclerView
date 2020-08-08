package com.lishide.recyclerview.scroll.listener;

import android.view.KeyEvent;
import android.view.View;

public interface OnItemKeyListener {
    void onItemKey(View view, int keyCode, KeyEvent event, int position);
}
