package com.lishide.recyclerview.scroll;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mRight;
    private int mBottom;

    public SpaceItemDecoration(int right, int bottom) {
        this.mRight = right;
        this.mBottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = mRight;
        outRect.bottom = mBottom;
    }
}
