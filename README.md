# ScrollRecyclerView
RecyclerView 横向 / 纵向滚动网格布局，适用于 Android 平板、Android TV 或其他定制化 Android 设备等，使用遥控器方向导航键控制列表滑动及 item 选择状态。

## 效果预览
![ScrollRecyclerView 效果演示](https://github.com/lishide/ScrollRecyclerView/raw/master/art/ScrollRecyclerView_art.gif "效果预览") 
---

[![](https://jitpack.io/v/lishide/ScrollRecyclerView.svg)](https://jitpack.io/#lishide/ScrollRecyclerView)
## 依赖
#### JitPack 引入方法
##### 1. 在 Project 下的 build.gradle 添加
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

##### 2. 在 Module 下的 build.gradle 添加
```groovy
dependencies {
    compile 'com.github.lishide:ScrollRecyclerView:v1.0.0'
}
```

## 使用

* **在 xml 中引用 ScrollRecyclerView**

```xml
<com.lishide.recyclerview.scroll.ScrollRecyclerView
    android:id="@+id/scroll_recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

* **初始化 ScrollRecyclerView，设置布局管理器、间距、适配器、数据等**

```java
// 初始化 ScrollRecyclerView
mScrollRecyclerView = (ScrollRecyclerView) findViewById(R.id.scroll_recycler_view);
// 设置动画
mScrollRecyclerView.setItemAnimator(new DefaultItemAnimator());
// 设置布局管理器：瀑布流式
mScrollRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
        StaggeredGridLayoutManager.HORIZONTAL));
// 根据需要设置间距等
int right = (int) getResources().getDimension(R.dimen.dp_20);
int bottom = (int) getResources().getDimension(R.dimen.dp_20);
RecyclerView.ItemDecoration spacingInPixel = new SpaceItemDecoration(right, bottom);
mScrollRecyclerView.addItemDecoration(spacingInPixel);
```

* **适配器中初始化控件并设置数据**

使用的 Adapter 就是正常的 Adapter。为了简单明了，在示例中用的是最普通的一种。当然了，你完全可以使用你常用的或是被封装过的高级 Adapter。

Adapter 中**比较重要的是**设置 itemView 可以获得焦点，并监听焦点变化。还有要设置 Tag，用来标记 item 的 position，后面有用。

```java
holder.itemView.setFocusable(true);
holder.itemView.setTag(position);
holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
});
```

会发现在 Adapter 中设置了许多监听器，目前有这四个：
* **item 选定监听（OnItemSelectedListener）**
* **item 点击监听（OnItemClickListener）**
* **item 长按监听（OnItemLongClickListener）**
* **遥控器其他按键监听（OnItemKeyListener）**

已将这四个 **Listener** 放在 lib 中，开发者根据需要直接设置和调用即可。

**要想实现咱们今天主要实现的功能——使用遥控器方向导航键控制列表滑动及 item 选择状态，下面的步骤很重要。**
* 在焦点监听器中，判断获得焦点时调用 `mOnItemSelectedListener.OnItemSelected(v, currentPosition);`，传入 view 和当前 position。

```java
if (hasFocus) {
    currentPosition = (int) holder.itemView.getTag();

    mOnItemSelectedListener.OnItemSelected(v, currentPosition);
}
```
* **滑动列表**

设置 item 选定监听器，然后在监听器中实现列表滑动。

```java
adapter.setOnItemSelectedListener(mOnItemSelectedListener);
```
```java
OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {
    @Override
    public void OnItemSelected(View view, int position) {
        mScrollRecyclerView.smoothHorizontalScrollToNext(position);
    }
};
```

> 实现滑动的功能就是这个`smoothHorizontalScrollToNext`方法了。具体的实现过程推荐下载 demo 看代码，demo 里面的注释非常全了。

好了，至此 **使用遥控器方向导航键控制列表滑动及 item 选择状态** 的功能大概完成了。顺便解释一下其他几个 Listener 的作用，OnItemClickListener、OnItemLongClickListener 这两个好理解，在其他的列表点击监听也会遇到过，就不过多说了。需要注意一点的是，item 长按监听要对 view 的点击事件返回值根据需要处理一下，比如 `return false` 会在长按事件结束后再触发一次点击事件，`return true`则只会触发长按事件。如果有特定需要，比如焦点在列表的某个 item 上时，按下了 OK 键，需要跳转到一个新的界面；或者按下 Menu 键做其他业务逻辑处理等等，此时应该设置监听——OnItemKeyListener（遥控器其他按键监听）。

**ScrollRecyclerView 的用处有一些局限性，手机端应该是用不到（我感觉，因为有触摸屏~），主要适用于 Android 平板、Android TV 或其他定制化 Android 设备等......这里，仍期待得到您的支持！**

**您在使用过程中，发现 bug 或有好的建议欢迎 issue、email (lishidezy@gmail.com)，如果感觉对你有帮助也欢迎点个 star，留下点印记吧。**


  [1]: https://github.com/lishide/ScrollRecyclerView
