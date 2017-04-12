package com.lishide.recyclerview.scroll;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

public class ScrollRecyclerView extends RecyclerView {
    private static final String TAG = ScrollRecyclerView.class.getSimpleName();
    // 一个滚动对象
    private Scroller mScroller;
    private int mLastX = 0;
    private int specialLeft, specialRight;
    private int childWidth;

    public ScrollRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public ScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 一个初始化方法，传入了一个上下文对象，用来初始化滚动对象
     *
     * @param context 上下文
     */
    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    /**
     * 重写计算滚动方法
     */
    @Override
    public void computeScroll() {
        if (mScroller != null && mScroller.computeScrollOffset()) {
//            Log.i("computeScroll", "curX:" + mScroller.getCurrX());
            scrollBy(mLastX - mScroller.getCurrX(), 0);
            mLastX = mScroller.getCurrX();
            postInvalidate();
        }
    }

    /**
     * 滚动到目标位置
     * 其中 (fx, fy) 表示最终要滚到的目标位置的坐标值，duration 表示期间滚动的耗时。
     *
     * @param fx       目标位置的X向坐标值
     * @param fy       目标位置的Y向坐标值
     * @param duration 滚动到目标位置所消耗的时间毫秒值
     */
    @SuppressWarnings("unused")
    public void smoothScrollTo(int fx, int fy, int duration) {
        int dx = 0;
        int dy = 0;
        // 计算变化的位移量
        if (fx != 0) {
            dx = fx - mScroller.getFinalX();
        }
        if (fy != 0) {
            dy = fy - mScroller.getFinalY();
        }
        Log.i(TAG, "fx:" + fx + ", getFinalX:" + mScroller.getFinalX() + ", dx:" + dx);
        smoothScrollBy(dx, dy, duration);
    }

    /**
     * 设置滚动的相对偏移
     */
    public void smoothScrollBy(int dx, int dy, int duration) {
        if (duration > 0) {
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);
        } else {
            // 设置mScroller的滚动偏移量
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        }
        // 重绘整个view，重绘过程会调用到computeScroll()方法。
        // 这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
        invalidate();
    }

    /**
     * 检查自动调节
     *
     * @param position 要检查的位置
     */
    @SuppressWarnings("unused")
    public void checkAutoAdjust(int position) {
        int childCount = getChildCount();
        // 获取可视范围内的选项的头尾位置
        int firstVisibleItemPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        int lastVisibleItemPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        Log.d(TAG, "childCount:" + childCount + ", position:" + position
                + ", firstVisibleItemPosition:" + firstVisibleItemPosition
                + "  lastVisibleItemPosition:" + lastVisibleItemPosition);
        if (position == (firstVisibleItemPosition + 1) || position == firstVisibleItemPosition) {
            leftScrollBy(position, firstVisibleItemPosition);
        } else if (position == (lastVisibleItemPosition - 1) || position == lastVisibleItemPosition) {
            rightScrollBy(position, lastVisibleItemPosition);
        }
    }

    /**
     * 当前位置需要向右平移
     *
     * @param position
     * @param firstVisibleItemPosition 可见的第一个Item的pos
     */
    private void leftScrollBy(int position, int firstVisibleItemPosition) {
        View leftChild = getChildAt(0);
        if (leftChild != null) {
            int startLeft = leftChild.getLeft();
            int endLeft = (position == firstVisibleItemPosition ? leftChild.getWidth() : 0);
            Log.d(TAG, "startLeft:" + startLeft + " endLeft" + endLeft);
            autoAdjustScroll(startLeft, endLeft);
        }
    }

    /**
     * 当前位置需要向左平移
     *
     * @param position
     * @param lastVisibleItemPosition 可见的最后一个Item的pos
     */
    private void rightScrollBy(int position, int lastVisibleItemPosition) {
        int childCount = getChildCount();
        View rightChild = getChildAt(childCount - 1);
        if (rightChild != null) {
            int startRight = rightChild.getRight() - getWidth();
            int endRight = (position == lastVisibleItemPosition ? (-1 * rightChild.getWidth()) : 0);
            Log.d(TAG, "startRight:" + startRight + " endRight:" + endRight);
            autoAdjustScroll(startRight, endRight);
        }
    }

    /**
     * 自动调节滑动
     *
     * @param start 滑动起始位置
     * @param end   滑动结束位置
     */
    private void autoAdjustScroll(int start, int end) {
        mLastX = start;
        mScroller.startScroll(start, 0, end - start, 0);
        postInvalidate();
    }

    /**
     * 将指定 item 平滑移动到整个 view 的中间位置
     *
     * @param position 指定的 item 的位置
     */
    public void smoothHorizontalScrollToNext(int position) {
        Log.d(TAG, "position=" + position);
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) getLayoutManager();
        int[] startItems = manager.findFirstVisibleItemPositions(null);
        int[] endItems = manager.findLastVisibleItemPositions(null);
        if (position == 0) {
            int parentWidth = getWidth();
            View firstView = getChildAt(0);
            mLastX = 1174;
            mScroller.startScroll(mLastX, 0, -500, 0);
            postInvalidate();

            childWidth = firstView.getWidth();
            int preLastPos = endItems[0] - 1;
            View specialView = getChildAt(preLastPos);
            if (specialView == null) return;
            specialLeft = specialView.getLeft();
            specialRight = parentWidth - specialLeft;
            Log.d(TAG, "一屏的倒数第二行位置是:" + preLastPos + ", 父容器宽度:" + parentWidth
                    + ", 超出位置(极右位置):" + specialLeft + ", 不达位置(极左位置):" + specialRight);
        }

        int targetPos = position - startItems[0];
        View targetView = getChildAt(targetPos);
        if (targetView == null) {
            Log.i(TAG, "TargetView is null!");
            return;
        }
        int targetLeft = targetView.getLeft();
        int targetRight = targetView.getRight();
        Log.d(TAG, "目标位置:" + targetPos + ", 目标左位置:" + targetLeft + ", 目标右位置:" + targetRight);

        if (targetLeft > specialLeft) {
            // 获得焦点的不全显示 item 将自动全显示。
            // 因此，到达极右位置只需移动下一个不全显示的偏置距离
            mLastX = targetLeft;
            mScroller.startScroll(targetLeft, 0, -childWidth / 2, 0);
            postInvalidate();
            Log.d(TAG, "<----");
        } else if (targetRight < specialRight) {
            // 到达极左位置
            mLastX = targetRight;
            mScroller.startScroll(targetRight, 0, childWidth / 2, 0);
            postInvalidate();
            Log.d(TAG, "---->");
        }
    }

}
